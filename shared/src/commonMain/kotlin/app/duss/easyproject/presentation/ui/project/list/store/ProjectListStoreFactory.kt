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

    private val projectsGetAllUseCase by inject<ProjectsGetAllUseCase>()

    fun create(): ProjectListStore =
        object : ProjectListStore, Store<ProjectListStore.Intent, ProjectListStore.State, Nothing> by storeFactory.create(
            name = ProjectListStore::class.simpleName,
            initialState = ProjectListStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object AddNewProject : Msg()
        data class NavigateToProjectDetails(val id: Long) : Msg()
        data object ProjectDetailsDone : Msg()
        data class UpdateItem(val project: Project) : Msg()
        data class DeleteItem(val id: Long) : Msg()
        data object ProjectListLoading : Msg()
        data class ProjectListLoaded(val projectList: List<Project>) : Msg()
        data class ProjectListFailed(val error: String?) : Msg()
        data class SearchValueUpdated(val searchValue: String) : Msg()
        data object LastPageLoaded : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<ProjectListStore.Intent, Unit, ProjectListStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> ProjectListStore.State) {
            loadProjectListByPage(page = 0)
        }

        override fun executeIntent(intent: ProjectListStore.Intent, getState: () -> ProjectListStore.State): Unit =
            when (intent) {
                is ProjectListStore.Intent.LoadProjectListByPage -> loadProjectListByPage(intent.page, getState().isLastPageLoaded)
                is ProjectListStore.Intent.UpdateSearchValue -> dispatch(Msg.SearchValueUpdated(intent.searchValue))
                ProjectListStore.Intent.AddNew -> dispatch(Msg.AddNewProject)
                is ProjectListStore.Intent.Details -> dispatch(Msg.NavigateToProjectDetails(intent.id))
                ProjectListStore.Intent.DetailsDone -> dispatch(Msg.ProjectDetailsDone)
                is ProjectListStore.Intent.DetailsChanged -> dispatch(Msg.UpdateItem(intent.project))
                is ProjectListStore.Intent.DetailsDeleted -> dispatch(Msg.DeleteItem(intent.deletedId))
            }

        private var loadProjectListByPageJob: Job? = null
        private fun loadProjectListByPage(
            page: Int,
            isLastPageLoaded: Boolean = false
        ) {
            if (loadProjectListByPageJob?.isActive == true) return
            if (isLastPageLoaded) return

            loadProjectListByPageJob = scope.launch {
                dispatch(Msg.ProjectListLoading)

                projectsGetAllUseCase
                    .execute(searchValue, page)
                    .onSuccess { list ->
                        if (list.isNotEmpty()) {
                            dispatch(Msg.ProjectListLoaded(list))
                        }
                        if (list.size < NetworkConstants.PageSize) {
                            dispatch(Msg.LastPageLoaded)
                        }
                    }
                    .onFailure { e ->
                        dispatch(Msg.ProjectListFailed(e.message))
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<ProjectListStore.State, Msg> {
        override fun ProjectListStore.State.reduce(msg: Msg): ProjectListStore.State =
            when (msg) {
                is Msg.ProjectListLoading -> copy(isLoading = true)
                is Msg.ProjectListLoaded -> ProjectListStore.State(projectList = projectList + msg.projectList)
                is Msg.ProjectListFailed -> copy(error = msg.error)
                is Msg.SearchValueUpdated -> copy(searchValue = msg.searchValue)
                Msg.LastPageLoaded -> copy(isLastPageLoaded = true)
                Msg.AddNewProject -> copy(projectId = -1)
                is Msg.NavigateToProjectDetails -> copy(projectId = msg.id)
                is Msg.ProjectDetailsDone -> copy(projectId = null)
                is Msg.DeleteItem -> copy(projectList = projectList.filterNot { it.id == msg.id })
                is Msg.UpdateItem -> copy(
                    projectList = if (projectList.any { it.id == msg.project.id }) {
                        projectList.map { if (it.id == msg.project.id) msg.project else it }
                    } else {
                        projectList + msg.project
                    }.sortedByDescending { it.code }
                )
            }
    }
}