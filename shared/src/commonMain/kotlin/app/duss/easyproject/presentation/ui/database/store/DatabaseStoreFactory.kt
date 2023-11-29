package app.duss.easyproject.presentation.ui.database.store

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

class DatabaseStoreFactory(
    private val storeFactory: StoreFactory,
): KoinComponent {

    private val pokemonRepository by inject<ProjectRepository>()

    fun create(): DatabaseStore =
        object : DatabaseStore, Store<DatabaseStore.Intent, DatabaseStore.State, Nothing> by storeFactory.create(
            name = "DatabaseStore",
            initialState = DatabaseStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object PokemonListLoading : Msg()
        data class PokemonListLoaded(val projectList: List<Project>) : Msg()
        data class PokemonListFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<DatabaseStore.Intent, Unit, DatabaseStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> DatabaseStore.State) {
            getFavoritePokemonList()
        }

        override fun executeIntent(intent: DatabaseStore.Intent, getState: () -> DatabaseStore.State): Unit = Unit

        private var getFavoritePokemonListJob: Job? = null
        private fun getFavoritePokemonList() {
            if (getFavoritePokemonListJob?.isActive == true) return

            getFavoritePokemonListJob = scope.launch {
                dispatch(Msg.PokemonListLoading)

//                pokemonRepository.getFavoritePokemonListFlow().collectLatest { favoritePokemonList ->
//                    dispatch(Msg.PokemonListLoaded(favoritePokemonList))
//                }
            }
        }
    }

    private object ReducerImpl: Reducer<DatabaseStore.State, Msg> {
        override fun DatabaseStore.State.reduce(msg: Msg): DatabaseStore.State =
            when (msg) {
                is Msg.PokemonListLoading -> copy(isLoading = true)
                is Msg.PokemonListLoaded -> DatabaseStore.State(projectList = msg.projectList)
                is Msg.PokemonListFailed -> copy(error = msg.error)
            }
    }
}