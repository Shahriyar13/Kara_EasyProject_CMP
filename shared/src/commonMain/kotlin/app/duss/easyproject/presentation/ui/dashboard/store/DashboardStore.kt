package app.duss.easyproject.presentation.ui.dashboard.store

import com.arkivanov.mvikotlin.core.store.Store

interface DashboardStore: Store<DashboardStore.Intent, DashboardStore.State, Nothing> {

    sealed class Intent {
        data class InputPokemonSearch(val search: String): Intent()
    }

    data class State(
        val search: String = "",
    )

}