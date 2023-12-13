package app.duss.easyproject.presentation.ui.company.store


import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.domain.usecase.company.GetAllCustomersUseCase
import app.duss.easyproject.domain.usecase.company.GetAllFreightForwardersUseCase
import app.duss.easyproject.domain.usecase.company.GetAllSuppliersUseCase
import app.duss.easyproject.domain.usecase.company.GetAllUseCase
import app.duss.easyproject.presentation.ui.company.CompanyFilter
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CompanyStoreFactory(
    private val storeFactory: StoreFactory,
    private val searchValue: String? = null,
    private val selectMode: Boolean = false,
    private val filter: CompanyFilter = CompanyFilter.ALL,
): KoinComponent {

    private val getAllUseCase by inject<GetAllUseCase>()
    private val getAllSuppliersUseCase by inject<GetAllSuppliersUseCase>()
    private val getAllCustomersUseCase by inject<GetAllCustomersUseCase>()
    private val getAllFreightForwardersUseCase by inject<GetAllFreightForwardersUseCase>()

    fun create(): CompanyStore =
        object : CompanyStore, Store<CompanyStore.Intent, CompanyStore.State, Nothing> by storeFactory.create(
            name = CompanyStore::class.simpleName,
            initialState = CompanyStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object SelectMode : Msg()
        data class Filer(val filter: CompanyFilter) : Msg()
        data object New : Msg()
        data class Edit(val id: Long?) : Msg()
        data object EditDone : Msg()
        data class Update(val item: Company) : Msg()
        data class Delete(val id: Long) : Msg()
        data object ListLoading : Msg()
        data class Selected(val item: Company) : Msg()
        data object SelectDone : Msg()
        data class ListLoaded(val list: List<Company>) : Msg()
        data class ListFailed(val error: String?) : Msg()
        data class SearchValueUpdated(val searchValue: String) : Msg()
        data object Refresh: Msg()
        data object LastPageLoaded : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<CompanyStore.Intent, Unit, CompanyStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> CompanyStore.State) {
            fetchList(
                page = 0,
                isLastPageLoaded = false,
                searchValue = searchValue,
                filter = filter,
            )
        }

        override fun executeIntent(intent: CompanyStore.Intent, getState: () -> CompanyStore.State) {
            when (intent) {
                is CompanyStore.Intent.Delete -> {
                    dispatch(Msg.Delete(intent.deletedId))
                }
                is CompanyStore.Intent.Edit -> {
                    dispatch(Msg.Edit(intent.id))
                }
                CompanyStore.Intent.EditDone -> {
                    dispatch(Msg.EditDone)
                }
                is CompanyStore.Intent.LoadByPage -> {
                    fetchList(
                        page = intent.page,
                        isLastPageLoaded = getState().isLastPageLoaded,
                        searchValue = getState().searchValue,
                        filter = getState().filter,
                    )
                }
                CompanyStore.Intent.New -> {
                    dispatch(Msg.New)
                }
                is CompanyStore.Intent.Update -> {
                    dispatch(Msg.Update(intent.item))
                }
                is CompanyStore.Intent.UpdateSearchValue -> {
                    dispatch(Msg.SearchValueUpdated(intent.searchValue))
                    dispatch(Msg.Refresh)
                    fetchList(
                        page = getState().page,
                        isLastPageLoaded = getState().isLastPageLoaded,
                        searchValue = getState().searchValue,
                        filter = getState().filter,
                    )
                }
                is CompanyStore.Intent.UpdateSelected -> {
                    dispatch(Msg.Selected(intent.item))
                }
                is CompanyStore.Intent.Filter -> {
                    dispatch(Msg.Filer(intent.filter))
                    dispatch(Msg.Refresh)
                    fetchList(
                        page = getState().page,
                        isLastPageLoaded = getState().isLastPageLoaded,
                        searchValue = getState().searchValue,
                        filter = getState().filter,
                    )
                }
            }
        }
        private var getAllJob: Job? = null
        private fun fetchList(
            page: Int,
            isLastPageLoaded: Boolean,
            filter: CompanyFilter,
            searchValue: String?,
        ) {
            if (getAllJob?.isActive == true) return
            if (isLastPageLoaded) return

            getAllJob = scope.launch {
                dispatch(Msg.ListLoading)

                delay(2000)
                when (filter) {
                    CompanyFilter.ALL -> {
                        getAllUseCase
                            .execute(searchValue, page)
                            .onSuccess { list ->
                                if (list.isNotEmpty()) {
                                    dispatch(Msg.ListLoaded(list))
                                }
                                if (list.size < NetworkConstants.PageSize) {
                                    if (list.isNotEmpty()) {
                                        dispatch(Msg.ListLoaded(list))
                                    }
                                    dispatch(Msg.LastPageLoaded)
                                }
                            }
                            .onFailure { e ->
                                dispatch(Msg.ListFailed(e.message))
                            }
                    }
                    CompanyFilter.Supplier -> {
                        getAllSuppliersUseCase
                            .execute(searchValue, page)
                            .onSuccess { list ->
                                if (list.isNotEmpty()) {
                                    dispatch(Msg.ListLoaded(list))
                                }
                                if (list.size < NetworkConstants.PageSize) {
                                    if (list.isNotEmpty()) {
                                        dispatch(Msg.ListLoaded(list))
                                    }
                                    dispatch(Msg.LastPageLoaded)
                                }
                            }
                            .onFailure { e ->
                                dispatch(Msg.ListFailed(e.message))
                            }
                    }
                    CompanyFilter.Customer -> {
                        getAllCustomersUseCase
                            .execute(searchValue, page)
                            .onSuccess { list ->
                                if (list.isNotEmpty()) {
                                    dispatch(Msg.ListLoaded(list))
                                }
                                if (list.size < NetworkConstants.PageSize) {
                                    if (list.isNotEmpty()) {
                                        dispatch(Msg.ListLoaded(list))
                                    }
                                    dispatch(Msg.LastPageLoaded)
                                }
                            }
                            .onFailure { e ->
                                dispatch(Msg.ListFailed(e.message))
                            }
                    }
                    CompanyFilter.FreightForwarder -> {
                        getAllFreightForwardersUseCase
                            .execute(searchValue, page)
                            .onSuccess { list ->
                                if (list.isNotEmpty()) {
                                    dispatch(Msg.ListLoaded(list))
                                }
                                if (list.size < NetworkConstants.PageSize) {
                                    if (list.isNotEmpty()) {
                                        dispatch(Msg.ListLoaded(list))
                                    }
                                    dispatch(Msg.LastPageLoaded)
                                }
                            }
                            .onFailure { e ->
                                dispatch(Msg.ListFailed(e.message))
                            }
                    }
                }
            }
        }
    }

    private object ReducerImpl: Reducer<CompanyStore.State, Msg> {
        override fun CompanyStore.State.reduce(msg: Msg): CompanyStore.State =
            when (msg) {
                is Msg.SelectDone -> copy(selectingDone = true)
                is Msg.ListLoading -> copy(isLoading = true)
                is Msg.ListLoaded -> copy(
                    list = list + msg.list,
                    isLoading = false,
                )
                is Msg.Selected -> copy(
                    selected = if (selected.any { it.id == msg.item.id }) {
                        selected - msg.item
                    } else {
                        selected + msg.item
                    }
                )
                is Msg.ListFailed -> copy(error = msg.error)
                is Msg.SearchValueUpdated -> copy(searchValue = msg.searchValue)
                Msg.LastPageLoaded -> copy(isLastPageLoaded = true, isLoading = false)
                Msg.SelectMode -> copy(selectMode = true)
                is Msg.Filer -> copy(filter = msg.filter)
                Msg.New -> copy(id = -1)
                is Msg.Edit -> copy(id = msg.id)
                is Msg.EditDone -> copy(id = null)
                is Msg.Delete -> copy(list = list.filterNot { it.id == msg.id })
                is Msg.Update -> copy(
                    list = if (list.any { it.id == msg.item.id }) {
                        list.map { if (it.id == msg.item.id) msg.item else it }
                    } else {
                        list + msg.item
                    }.sortedByDescending { it.creationTime }
                )
                Msg.Refresh -> copy(page = 0, isLastPageLoaded = false, list = emptyList(), error = null)
            }
    }
}