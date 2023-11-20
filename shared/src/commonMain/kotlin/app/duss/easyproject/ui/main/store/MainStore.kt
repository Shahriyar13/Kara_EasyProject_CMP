package app.duss.easyproject.ui.main.store

import com.arkivanov.mvikotlin.core.store.Store

interface MainStore: Store<MainStore.Intent, MainStore.State, Nothing> {

    sealed class Intent {
        data class InputPokemonSearch(val search: String): Intent()
    }

    data class State(
        val search: String = "",
    )

}