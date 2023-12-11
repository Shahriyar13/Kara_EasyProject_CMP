package app.duss.easyproject.presentation.ui.item.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemStoreFactory(
    private val storeFactory: StoreFactory,
): KoinComponent {

    private val getAll by inject<GetAllUseCase>()

    fun create(): ItemStore =
        object : ItemStore, Store<ItemStore.Intent, ItemStore.State, Nothing> by storeFactory.create(
            name = "ItemStore",
            initialState = ItemStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object PokemonListLoading : Msg()
        data class PokemonListLoaded(val projectList: List<Project>) : Msg()
        data class PokemonListFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<ItemStore.Intent, Unit, ItemStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> ItemStore.State) {
            getFavoritePokemonList()
        }

        override fun executeIntent(intent: ItemStore.Intent, getState: () -> ItemStore.State): Unit = Unit

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

    private object ReducerImpl: Reducer<ItemStore.State, Msg> {
        override fun ItemStore.State.reduce(msg: Msg): ItemStore.State =
            when (msg) {
                is Msg.PokemonListLoading -> copy(isLoading = true)
                is Msg.PokemonListLoaded -> ItemStore.State(projectList = msg.projectList)
                is Msg.PokemonListFailed -> copy(error = msg.error)
            }
    }
}