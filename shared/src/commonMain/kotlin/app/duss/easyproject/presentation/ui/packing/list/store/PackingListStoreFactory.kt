package app.duss.easyproject.presentation.ui.packing.list.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.domain.usecase.packing.GetAllUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class PackingListStoreFactory(
    private val storeFactory: StoreFactory,
    private val searchValue: String?,
): KoinComponent {

    private val getAllUseCase by inject<GetAllUseCase>()

    fun create(): PackingListStore =
        object : PackingListStore, Store<PackingListStore.Intent, PackingListStore.State, Nothing> by storeFactory.create(
            name = PackingListStore::class.simpleName,
            initialState = PackingListStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object AddNew : Msg()
        data class NavigateToDetails(val id: Long) : Msg()
        data object DetailsDone : Msg()
        data class UpdateItem(val item: Packing) : Msg()
        data class DeleteItem(val id: Long) : Msg()
        data object ListLoading : Msg()
        data class ListLoaded(val list: List<Packing>) : Msg()
        data class ListFailed(val error: String?) : Msg()
        data class SearchValueUpdated(val searchValue: String) : Msg()
        data object LastPageLoaded : Msg()
        data class LastPage(val page: Int) : Msg()
        data object Refresh: Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<PackingListStore.Intent, Unit, PackingListStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> PackingListStore.State) {
            fetchList(
                page = 0,
                searchValue = searchValue,
                isLastPageLoaded = false,
            )
        }

        override fun executeIntent(intent: PackingListStore.Intent, getState: () -> PackingListStore.State): Unit =
            when (intent) {
                is PackingListStore.Intent.LoadByPage -> {
                    dispatch(Msg.Refresh)
                    fetchList(
                        page = intent.page,
                        isLastPageLoaded = getState().isLastPageLoaded,
                        searchValue = getState().searchValue,
                    )
                }
                is PackingListStore.Intent.UpdateSearchValue -> {
                    dispatch(Msg.SearchValueUpdated(intent.searchValue))
                    dispatch(Msg.Refresh)
                    fetchList(
                        page = getState().page,
                        isLastPageLoaded = getState().isLastPageLoaded,
                        searchValue = getState().searchValue,
                    )
                }
                PackingListStore.Intent.AddNew -> {
                    dispatch(Msg.AddNew)
                }
                is PackingListStore.Intent.Details -> {
                    dispatch(Msg.NavigateToDetails(intent.id))
                }
                PackingListStore.Intent.DetailsDone -> {
                    dispatch(Msg.DetailsDone)
                }
                is PackingListStore.Intent.DetailsChanged -> {
                    dispatch(Msg.UpdateItem(intent.item))
                }
                is PackingListStore.Intent.DetailsDeleted -> {
                    dispatch(Msg.DeleteItem(intent.deletedId))
                }
                is PackingListStore.Intent.Refresh -> {
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
                        dispatch(Msg.LastPage(page))
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

    private object ReducerImpl: Reducer<PackingListStore.State, Msg> {
        override fun PackingListStore.State.reduce(msg: Msg): PackingListStore.State =
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
                is Msg.LastPage -> copy(page = msg.page)
            }
    }
}