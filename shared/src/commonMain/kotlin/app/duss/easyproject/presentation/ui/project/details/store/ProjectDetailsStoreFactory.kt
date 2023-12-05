package app.duss.easyproject.presentation.ui.project.details.store

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

internal class ProjectDetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val projectId: Long?
): KoinComponent {
    private val projectRepository by inject<ProjectRepository>()

    fun create(): ProjectDetailsStore =
        object : ProjectDetailsStore, Store<ProjectDetailsStore.Intent, ProjectDetailsStore.State, Nothing> by storeFactory.create(
            name = ProjectDetailsStore::class.simpleName,
            initialState = ProjectDetailsStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object InfoLoading : Msg()
        data class InfoLoaded(val project: Project) : Msg()
        data class InfoFailed(val error: String?) : Msg()
        data class FavoriteStateUpdated(val isFavorite: Boolean) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<ProjectDetailsStore.Intent, Unit, ProjectDetailsStore.State, Msg, Nothing>(appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> ProjectDetailsStore.State) {
            loadProjectById(projectId)
        }

        override fun executeIntent(intent: ProjectDetailsStore.Intent, getState: () -> ProjectDetailsStore.State): Unit =
            when (intent) {
                is ProjectDetailsStore.Intent.UpdateFavoriteState -> toggleFavorite(projectId, intent.isFavorite)
            }

        private var loadProjectByIdJob: Job? = null
        private fun loadProjectById(id: Long?) {
            if (loadProjectByIdJob?.isActive == true) return

            loadProjectByIdJob = scope.launch {
                dispatch(Msg.InfoLoading)

                projectRepository
                    .getProjectById(id)
                    .onSuccess { project ->
                        dispatch(Msg.InfoLoaded(project))
                    }
                    .onFailure { e ->
                        dispatch(Msg.InfoFailed(e.message))
                    }
            }
        }

        private var toggleFavoriteJob: Job? = null
        private fun toggleFavorite(id: Long?, isFavorite: Boolean) {
            if (toggleFavoriteJob?.isActive == true) return

            toggleFavoriteJob = scope.launch {
//                pokemonRepository.updatePokemonFavoriteState(
//                    name = name,
//                    isFavorite = isFavorite,
//                )

                dispatch(Msg.FavoriteStateUpdated(isFavorite))
            }
        }
    }

    private object ReducerImpl: Reducer<ProjectDetailsStore.State, Msg> {
        override fun ProjectDetailsStore.State.reduce(msg: Msg): ProjectDetailsStore.State =
            when (msg) {
                is Msg.InfoLoading -> ProjectDetailsStore.State(isLoading = true)
                is Msg.InfoLoaded -> ProjectDetailsStore.State(projectInfo = msg.project)
                is Msg.InfoFailed -> ProjectDetailsStore.State(error = msg.error)
                is Msg.FavoriteStateUpdated -> copy(projectInfo = projectInfo?.copy())
            }
    }

}