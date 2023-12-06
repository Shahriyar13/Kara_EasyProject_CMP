package app.duss.easyproject.presentation.ui.mproject.store

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

internal class mProjectStoreFactory(
    private val storeFactory: StoreFactory,
    private val searchValue: String?,
): KoinComponent {

    private val projectRepository by inject<ProjectRepository>()

    fun create(): mProjectStore =
        object : mProjectStore, Store<mProjectStore.Intent, mProjectStore.State, Nothing> by storeFactory.create(
            name = mProjectStore::class.simpleName,
            initialState = mProjectStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object AddNewProject : Msg()
        data class NavigateToProjectDetails(val id: Long) : Msg()
        data object ProjectDetailsDone : Msg()
        data object ProjectListLoading : Msg()
        data class ProjectListLoaded(val projectList: List<Project>) : Msg()
        data class ProjectListFailed(val error: String?) : Msg()
        data class SearchValueUpdated(val searchValue: String) : Msg()
        data object LastPageLoaded : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<mProjectStore.Intent, Unit, mProjectStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> mProjectStore.State) {
            loadProjectListByPage(page = 0)
        }

        override fun executeIntent(intent: mProjectStore.Intent, getState: () -> mProjectStore.State): Unit =
            when (intent) {
                is mProjectStore.Intent.LoadProjectListByPage -> loadProjectListByPage(intent.page, getState().isLastPageLoaded)
                is mProjectStore.Intent.UpdateSearchValue -> dispatch(Msg.SearchValueUpdated(intent.searchValue))
                mProjectStore.Intent.AddNew -> dispatch(Msg.AddNewProject)
                is mProjectStore.Intent.Details -> dispatch(Msg.NavigateToProjectDetails(intent.id))
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

    private object ReducerImpl: Reducer<mProjectStore.State, Msg> {
        override fun mProjectStore.State.reduce(msg: Msg): mProjectStore.State =
            when (msg) {
                is Msg.ProjectListLoading -> copy(isLoading = true)
                is Msg.ProjectListLoaded -> mProjectStore.State(projectList = projectList + msg.projectList)
                is Msg.ProjectListFailed -> copy(error = msg.error)
                is Msg.SearchValueUpdated -> copy(searchValue = msg.searchValue)
                Msg.LastPageLoaded -> copy(isLastPageLoaded = true)
                Msg.AddNewProject -> copy(projectId = -1)
                is Msg.NavigateToProjectDetails -> copy(projectId = msg.id)
                is Msg.ProjectDetailsDone -> copy(projectId = null)
            }
    }
}