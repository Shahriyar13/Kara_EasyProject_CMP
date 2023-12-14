package app.duss.easyproject.presentation.ui.person.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.usecase.person.GetAllUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PersonStoreFactory(
    private val storeFactory: StoreFactory,
    private val searchValue: String? = null,
    private val selectMode: Boolean = false,
) : KoinComponent {

    private val getAllUseCase by inject<GetAllUseCase>()

    fun create(): PersonStore =
        object : PersonStore,
            Store<PersonStore.Intent, PersonStore.State, Nothing> by storeFactory.create(
                name = PersonStore::class.simpleName,
                initialState = PersonStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data object SelectMode : Msg()
        data object New : Msg()
        data object Refresh: Msg()
        data class Edit(val id: Long?) : Msg()
        data object EditDone : Msg()
        data class Update(val item: Person) : Msg()
        data class Delete(val id: Long) : Msg()
        data object ListLoading : Msg()
        data class Selected(val item: Person) : Msg()
        data object SelectDone : Msg()
        data class ListLoaded(val list: List<Person>) : Msg()
        data class ListFailed(val error: String?) : Msg()
        data class SearchValueUpdated(val searchValue: String) : Msg()
        data object LastPageLoaded: Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<PersonStore.Intent, Unit, PersonStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> PersonStore.State) {
            if (selectMode) {
                dispatch(Msg.SelectMode)
            }
            fetchList(page = 0, isLastPageLoaded = false, searchValue = searchValue)
        }

        override fun executeIntent(intent: PersonStore.Intent, getState: () -> PersonStore.State) {
            when (intent) {
                is PersonStore.Intent.Delete -> {
                    dispatch(Msg.Delete(intent.deletedId))
                }
                is PersonStore.Intent.Edit -> {
                    dispatch(Msg.Edit(intent.id))
                }
                PersonStore.Intent.EditDone -> {
                    dispatch(Msg.EditDone)
                }
                is PersonStore.Intent.LoadByPage -> {
                    fetchList(
                        intent.page,
                        getState().isLastPageLoaded,
                        getState().searchValue,
                    )
                }
                PersonStore.Intent.New -> {
                    dispatch(Msg.New)
                }
                is PersonStore.Intent.Update -> {
                    dispatch(Msg.Update(intent.item))
                }
                is PersonStore.Intent.UpdateSearchValue -> {
                    dispatch(Msg.Refresh)
                    dispatch(Msg.SearchValueUpdated(intent.searchValue))
                    fetchList(
                        getState().page,
                        getState().isLastPageLoaded,
                        getState().searchValue,
                    )
                }
                is PersonStore.Intent.UpdateSelected -> {
                    dispatch(Msg.Selected(intent.item))
                }
            }
        }

        private var getAllJob: Job? = null
        private fun fetchList(
            page: Int,
            isLastPageLoaded: Boolean = false,
            searchValue: String? = null,
        ) {
            if (getAllJob?.isActive == true) return
            if (isLastPageLoaded) return

            getAllJob = scope.launch {
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

    private object ReducerImpl: Reducer<PersonStore.State, Msg> {
        override fun PersonStore.State.reduce(msg: Msg): PersonStore.State =
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