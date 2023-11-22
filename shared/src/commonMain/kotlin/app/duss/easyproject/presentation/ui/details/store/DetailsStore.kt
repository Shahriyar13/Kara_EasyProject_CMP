package app.duss.easyproject.presentation.ui.details.store

import app.duss.easyproject.core.model.PokemonInfo
import com.arkivanov.mvikotlin.core.store.Store

interface DetailsStore: Store<DetailsStore.Intent, DetailsStore.State, Nothing> {

    sealed class Intent {
        data class UpdatePokemonFavoriteState(val isFavorite: Boolean): Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val pokemonInfo: PokemonInfo? = null
    )

}