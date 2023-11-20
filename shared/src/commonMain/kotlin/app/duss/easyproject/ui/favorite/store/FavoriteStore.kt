package app.duss.easyproject.ui.favorite.store

import com.arkivanov.mvikotlin.core.store.Store
import app.duss.easyproject.core.model.Project

interface FavoriteStore: Store<FavoriteStore.Intent, FavoriteStore.State, Nothing> {

    sealed class Intent

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val projectList: List<Project> = emptyList(),
    )

}