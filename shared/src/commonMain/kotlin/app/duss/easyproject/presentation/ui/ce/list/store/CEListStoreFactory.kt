package app.duss.easyproject.presentation.ui.ce.list.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.usecase.ce.GetAllUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class CEListStoreFactory(
    private val storeFactory: StoreFactory,
    private val searchValue: String?,
): KoinComponent {

    private val getAllUseCase by inject<GetAllUseCase>()

    fun create(): CEListStore =
        object : CEListStore, Store<CEListStore.Intent, CEListStore.State, Nothing> by storeFactory.create(
            name = CEListStore::class.simpleName,
            initialState = CEListStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object AddNew : Msg()
        data class NavigateToDetails(val id: Long) : Msg()
        data object DetailsDone : Msg()
        data class UpdateItem(val item: CustomerEnquiry) : Msg()
        data class DeleteItem(val id: Long) : Msg()
        data object ListLoading : Msg()
        data class ListLoaded(val list: List<CustomerEnquiry>) : Msg()
        data class ListFailed(val error: String?) : Msg()
        data class SearchValueUpdated(val searchValue: String) : Msg()
        data object LastPageLoaded : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<CEListStore.Intent, Unit, CEListStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> CEListStore.State) {
            loadListByPage(page = 0, searchValue = searchValue, isLastPageLoaded = false)
        }

        override fun executeIntent(intent: CEListStore.Intent, getState: () -> CEListStore.State): Unit =
            when (intent) {
                is CEListStore.Intent.LoadByPage -> loadListByPage(intent.page, getState().searchValue, getState().isLastPageLoaded)
                is CEListStore.Intent.UpdateSearchValue -> dispatch(Msg.SearchValueUpdated(intent.searchValue))
                CEListStore.Intent.AddNew -> dispatch(Msg.AddNew)
                is CEListStore.Intent.Details -> dispatch(Msg.NavigateToDetails(intent.id))
                CEListStore.Intent.DetailsDone -> dispatch(Msg.DetailsDone)
                is CEListStore.Intent.DetailsChanged -> dispatch(Msg.UpdateItem(intent.item))
                is CEListStore.Intent.DetailsDeleted -> dispatch(Msg.DeleteItem(intent.deletedId))
            }

        private var loadListByPageJob: Job? = null
        private fun loadListByPage(
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
                        if (list.isNotEmpty()) {
                            dispatch(Msg.ListLoaded(list))
                        }
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

    private object ReducerImpl: Reducer<CEListStore.State, Msg> {
        override fun CEListStore.State.reduce(msg: Msg): CEListStore.State =
            when (msg) {
                is Msg.ListLoading -> copy(isLoading = true)
                is Msg.ListLoaded -> CEListStore.State(list = list + msg.list)
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
            }
    }
}