package app.duss.easyproject.presentation.ui.favorite.store

import app.duss.easyproject.domain.entity.Project
import com.arkivanov.mvikotlin.core.store.Store

interface FavoriteStore: Store<FavoriteStore.Intent, FavoriteStore.State, Nothing> {

    sealed class Intent

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val projectList: List<Project> = emptyList(),
    )

}