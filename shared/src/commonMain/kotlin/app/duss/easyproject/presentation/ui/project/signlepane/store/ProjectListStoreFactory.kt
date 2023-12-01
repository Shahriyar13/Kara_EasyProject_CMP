package app.duss.easyproject.presentation.ui.project.signlepane.store

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

internal class ProjectListStoreFactory(
    private val storeFactory: StoreFactory,
    private val searchValue: String,
): KoinComponent {

    private val projectRepository by inject<ProjectRepository>()

    fun create(): ProjectListStore =
        object : ProjectListStore, Store<ProjectListStore.Intent, ProjectListStore.State, Nothing> by storeFactory.create(
            name = "ProjectStore",
            initialState = ProjectListStore.State(),
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

    private inner class ExecutorImpl : CoroutineExecutor<ProjectListStore.Intent, Unit, ProjectListStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> ProjectListStore.State) {
            loadProjectListByPage(page = 0)
        }

        override fun executeIntent(intent: ProjectListStore.Intent, getState: () -> ProjectListStore.State): Unit =
            when (intent) {
                is ProjectListStore.Intent.LoadProjectListByPage -> loadProjectListByPage(intent.page, getState().isLastPageLoaded)
                is ProjectListStore.Intent.UpdateSearchValue -> dispatch(
                    Msg.SearchValueUpdated(
                        intent.searchValue
                    )
                )
                ProjectListStore.Intent.AddNew -> TODO()
                is ProjectListStore.Intent.Details -> TODO()
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

    private object ReducerImpl: Reducer<ProjectListStore.State, Msg> {
        override fun ProjectListStore.State.reduce(msg: Msg): ProjectListStore.State =
            when (msg) {
                is Msg.ProjectListLoading -> copy(isLoading = true)
                is Msg.ProjectListLoaded -> ProjectListStore.State(projectList = projectList + msg.projectList)
                is Msg.ProjectListFailed -> copy(error = msg.error)
                is Msg.SearchValueUpdated -> copy(searchValue = msg.searchValue)
                Msg.LastPageLoaded -> copy(isLastPageLoaded = true)
            }
    }
}