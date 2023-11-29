package app.duss.easyproject.presentation.ui.company.store

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

class CompanyStoreFactory(
    private val storeFactory: StoreFactory,
): KoinComponent {

    private val pokemonRepository by inject<ProjectRepository>()

    fun create(): CompanyStore =
        object : CompanyStore, Store<CompanyStore.Intent, CompanyStore.State, Nothing> by storeFactory.create(
            name = "DatabaseStore",
            initialState = CompanyStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object PokemonListLoading : Msg()
        data class PokemonListLoaded(val projectList: List<Project>) : Msg()
        data class PokemonListFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<CompanyStore.Intent, Unit, CompanyStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> CompanyStore.State) {
            getFavoritePokemonList()
        }

        override fun executeIntent(intent: CompanyStore.Intent, getState: () -> CompanyStore.State): Unit = Unit

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

    private object ReducerImpl: Reducer<CompanyStore.State, Msg> {
        override fun CompanyStore.State.reduce(msg: Msg): CompanyStore.State =
            when (msg) {
                is Msg.PokemonListLoading -> copy(isLoading = true)
                is Msg.PokemonListLoaded -> CompanyStore.State(list = msg.projectList)
                is Msg.PokemonListFailed -> copy(error = msg.error)
            }
    }
}