package app.duss.easyproject.presentation.ui.quotation.list.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.domain.entity.Quotation
import app.duss.easyproject.domain.usecase.quotation.GetAllUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class QuotationListStoreFactory(
    private val storeFactory: StoreFactory,
    private val searchValue: String?,
): KoinComponent {

    private val getAllUseCase by inject<GetAllUseCase>()

    fun create(): QuotationListStore =
        object : QuotationListStore, Store<QuotationListStore.Intent, QuotationListStore.State, Nothing> by storeFactory.create(
            name = QuotationListStore::class.simpleName,
            initialState = QuotationListStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object AddNew : Msg()
        data class NavigateToDetails(val id: Long) : Msg()
        data object DetailsDone : Msg()
        data class UpdateItem(val item: Quotation) : Msg()
        data class DeleteItem(val id: Long) : Msg()
        data object ListLoading : Msg()
        data class ListLoaded(val list: List<Quotation>) : Msg()
        data class ListFailed(val error: String?) : Msg()
        data class SearchValueUpdated(val searchValue: String) : Msg()
        data object LastPageLoaded : Msg()
        data object Refresh: Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<QuotationListStore.Intent, Unit, QuotationListStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> QuotationListStore.State) {
            fetchList(page = 0, searchValue = searchValue, isLastPageLoaded = false)
        }

        override fun executeIntent(intent: QuotationListStore.Intent, getState: () -> QuotationListStore.State): Unit =
            when (intent) {
                is QuotationListStore.Intent.LoadByPage -> {
                    fetchList(intent.page, getState().searchValue, getState().isLastPageLoaded)
                }
                is QuotationListStore.Intent.UpdateSearchValue -> {
                    dispatch(Msg.SearchValueUpdated(intent.searchValue))
                    dispatch(Msg.Refresh)
                    fetchList(
                        page = getState().page,
                        isLastPageLoaded = getState().isLastPageLoaded,
                        searchValue = getState().searchValue,
                    )
                }
                QuotationListStore.Intent.AddNew -> {
                    dispatch(Msg.AddNew)
                }
                is QuotationListStore.Intent.Details -> {
                    dispatch(Msg.NavigateToDetails(intent.id))
                }
                QuotationListStore.Intent.DetailsDone -> {
                    dispatch(Msg.DetailsDone)
                }
                is QuotationListStore.Intent.DetailsChanged -> {
                    dispatch(Msg.UpdateItem(intent.item))
                }
                is QuotationListStore.Intent.DetailsDeleted -> {
                    dispatch(Msg.DeleteItem(intent.deletedId))
                }
                is QuotationListStore.Intent.Refresh -> {
                    dispatch(Msg.Refresh)
                    fetchList(
                        page = getState().page,
                        isLastPageLoaded = getState().isLastPageLoaded,
                        searchValue = getState().searchValue,
                    )
                }
            }

        private var loadListByPageJob: Job? = null
        private fun fetchList(
            page: Int,
            searchValue: String? = null,
            isLastPageLoaded: Boolean = false
        ) {
            if (loadListByPageJob?.isActive == true) return
            if (isLastPageLoaded) return

            loadListByPageJob = scope.launch {
                dispatch(Msg.ListLoading)

                getAllUseCase
                    .execute(searchValue, page)
                    .onSuccess { list ->
                        dispatch(Msg.ListLoaded(list))
                        if (list.size < NetworkConstants.PageSize) {
                            dispatch(Msg.LastPageLoaded)
                        }
                    }
                    .onFailure { e ->
                        dispatch(Msg.ListFailed(e.message))
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<QuotationListStore.State, Msg> {
        override fun QuotationListStore.State.reduce(msg: Msg): QuotationListStore.State =
            when (msg) {
                is Msg.ListLoading -> copy(isLoading = true)
                is Msg.ListLoaded -> copy(list = list + msg.list, isLoading = false)
                is Msg.ListFailed -> copy(error = msg.error)
                is Msg.SearchValueUpdated -> copy(searchValue = msg.searchValue)
                Msg.LastPageLoaded -> copy(isLastPageLoaded = true)
                Msg.AddNew -> copy(id = -1)
                is Msg.NavigateToDetails -> copy(id = msg.id)
                is Msg.DetailsDone -> copy(id = null)
                is Msg.DeleteItem -> copy(list = list.filterNot { it.id == msg.id })
                is Msg.UpdateItem -> copy(
                    list = if (list.any { it.id == msg.item.id }) {
                        list.map { if (it.id == msg.item.id) msg.item else it }
                    } else {
                        list + msg.item
                    }.sortedByDescending { it.code }
                )
                Msg.Refresh -> copy(page = 0, isLastPageLoaded = false, list = emptyList(), error = null)
            }
    }
}