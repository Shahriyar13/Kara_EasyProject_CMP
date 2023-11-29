package app.duss.easyproject.presentation.ui.project.list.store

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.utils.appDispatchers
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ProjectStoreFactory(
    private val storeFactory: StoreFactory,
    private val searchValue: String,
): KoinComponent {

    private val projectRepository by inject<ProjectRepository>()

    fun create(): ProjectStore =
        object : ProjectStore, Store<ProjectStore.Intent, ProjectStore.State, Nothing> by storeFactory.create(
            name = "ProjectStore",
            initialState = ProjectStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object ProjectListLoading : Msg()
        data class ProjectListLoaded(val projectList: List<Project>) : Msg()
        data class ProjectListFailed(val error: String?) : Msg()
        data class SearchValueUpdated(val searchValue: String) : Msg()
        data object LastPageLoaded : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<ProjectStore.Intent, Unit, ProjectStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> ProjectStore.State) {
            loadProjectListByPage(page = 0)
        }

        override fun executeIntent(intent: ProjectStore.Intent, getState: () -> ProjectStore.State): Unit =
            when (intent) {
                is ProjectStore.Intent.LoadProjectListByPage -> loadProjectListByPage(intent.page, getState().isLastPageLoaded)
                is ProjectStore.Intent.UpdateSearchValue -> dispatch(Msg.SearchValueUpdated(intent.searchValue))
            }

        private var loadProjectListByPageJob: Job? = null
        private fun loadProjectListByPage(
            page: Long,
            isLastPageLoaded: Boolean = false
        ) {
            if (loadProjectListByPageJob?.isActive == true) return
            if (isLastPageLoaded) return

            loadProjectListByPageJob = scope.launch {
                dispatch(Msg.ProjectListLoading)

                projectRepository
                    .getProjectList(page)
                    .onSuccess { list ->
                        if (list.isEmpty()) {
                            dispatch(Msg.LastPageLoaded)
                        } else {
                            dispatch(Msg.ProjectListLoaded(list))
                        }
                    }
                    .onFailure { e ->
                        dispatch(Msg.ProjectListFailed(e.message))
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<ProjectStore.State, Msg> {
        override fun ProjectStore.State.reduce(msg: Msg): ProjectStore.State =
            when (msg) {
                is Msg.ProjectListLoading -> copy(isLoading = true)
                is Msg.ProjectListLoaded -> ProjectStore.State(projectList = projectList + msg.projectList)
                is Msg.ProjectListFailed -> copy(error = msg.error)
                is Msg.SearchValueUpdated -> copy(searchValue = msg.searchValue)
                Msg.LastPageLoaded -> copy(isLastPageLoaded = true)
            }
    }
}