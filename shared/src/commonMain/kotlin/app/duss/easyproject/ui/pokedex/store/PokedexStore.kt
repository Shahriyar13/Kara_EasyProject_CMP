package app.duss.easyproject.ui.pokedex.store

import com.arkivanov.mvikotlin.core.store.Store
import app.duss.easyproject.core.model.Pokemon

interface PokedexStore: Store<PokedexStore.Intent, PokedexStore.State, Nothing> {

    sealed class Intent {
        data class LoadPokemonListByPage(val page: Long): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val pokemonList: List<Pokemon> = emptyList(),
        val searchValue: String = "",
    )
}