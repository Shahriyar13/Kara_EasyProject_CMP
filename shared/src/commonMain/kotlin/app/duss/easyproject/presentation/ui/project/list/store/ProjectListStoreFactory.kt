package app.duss.easyproject.presentation.ui.project.list.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.usecase.project.ProjectsGetAllUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ProjectListStoreFactory(
    private val storeFactory: StoreFactory,
    private val searchValue: String?,
): KoinComponent {

    private val getAllUseCase by inject<ProjectsGetAllUseCase>()

    fun create(): ProjectListStore =
        object : ProjectListStore, Store<ProjectListStore.Intent, ProjectListStore.State, Nothing> by storeFactory.create(
            name = ProjectListStore::class.simpleName,
            initialState = ProjectListStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object AddNew : Msg()
        data class NavigateToDetails(val id: Long) : Msg()
        data object DetailsDone : Msg()
        data class UpdateItem(val item: Project) : Msg()
        data class DeleteItem(val id: Long) : Msg()
        data object ListLoading : Msg()
        data class LastPage(val page: Int) : Msg()
        data class ListLoaded(val list: List<Project>) : Msg()
        data class ListFailed(val error: String?) : Msg()
        data class SearchValueUpdated(val searchValue: String) : Msg()
        data object LastPageLoaded : Msg()
        data object Refresh: Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<ProjectListStore.Intent, Unit, ProjectListStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> ProjectListStore.State) {
            fetchList(
                page = 0,
                searchValue = searchValue,
                isLastPageLoaded = false,
            )
        }

        override fun executeIntent(intent: ProjectListStore.Intent, getState: () -> ProjectListStore.State): Unit =
            when (intent) {
                is ProjectListStore.Intent.LoadByPage -> {
                    fetchList(
                        page = intent.page,
                        isLastPageLoaded = getState().isLastPageLoaded,
                        searchValue = getState().searchValue,
                    )
                }
                is ProjectListStore.Intent.UpdateSearchValue -> {
                    dispatch(Msg.SearchValueUpdated(intent.searchValue))
                    dispatch(Msg.Refresh)
                    fetchList(
                        page = getState().page,
                        isLastPageLoaded = getState().isLastPageLoaded,
                        searchValue = getState().searchValue,
                    )
                }
                ProjectListStore.Intent.AddNew -> {
                    dispatch(Msg.AddNew)
                }
                is ProjectListStore.Intent.Edit -> {
                    dispatch(Msg.NavigateToDetails(intent.id))
                }
                ProjectListStore.Intent.DetailsDone -> {
                    dispatch(Msg.DetailsDone)
                }
                is ProjectListStore.Intent.DetailsChanged -> {
                    dispatch(Msg.UpdateItem(intent.project))
                }
                is ProjectListStore.Intent.DetailsDeleted -> {
                    dispatch(Msg.DeleteItem(intent.deletedId))
                }
                is ProjectListStore.Intent.Refresh -> {
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

    private object ReducerImpl: Reducer<ProjectListStore.State, Msg> {
        override fun ProjectListStore.State.reduce(msg: Msg): ProjectListStore.State =
            when (msg) {
                is Msg.ListLoading -> copy(isLoading = true)
                is Msg.ListLoaded -> copy(
                    list = list + msg.list, isLoading = false)
                is Msg.ListFailed -> copy(error = msg.error)
                is Msg.SearchValueUpdated -> copy(searchValue = msg.searchValue)
                Msg.LastPageLoaded -> copy(isLastPageLoaded = true, isLoading = false)
                Msg.AddNew -> copy(id = -1)
                is Msg.NavigateToDetails -> copy(id = msg.id)
                is Msg.DetailsDone -> copy(id = null)
                is Msg.DeleteItem -> copy(list = list.filterNot { it.id == msg.id })
                is Msg.UpdateItem -> copy(
                    isLoading = false,
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