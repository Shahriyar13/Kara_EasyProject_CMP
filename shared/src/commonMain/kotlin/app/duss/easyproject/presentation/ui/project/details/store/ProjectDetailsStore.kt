package app.duss.easyproject.presentation.ui.project.details.store

import app.duss.easyproject.domain.entity.Project
import com.arkivanov.mvikotlin.core.store.Store

interface ProjectDetailsStore: Store<ProjectDetailsStore.Intent, ProjectDetailsStore.State, Nothing> {

    sealed class Intent {
        data class UpdateFavoriteState(val isFavorite: Boolean): Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val projectInfo: Project? = null
    )

}