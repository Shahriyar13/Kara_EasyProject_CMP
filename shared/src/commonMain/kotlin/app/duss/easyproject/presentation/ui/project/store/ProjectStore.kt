package app.duss.easyproject.presentation.ui.project.store

import com.arkivanov.mvikotlin.core.store.Store

interface ProjectStore: Store<ProjectStore.Intent, ProjectStore.State, Nothing> {

    sealed class Intent {
        data object SinglePane: Intent()
        data object MultiPane: Intent()
    }

    data class State(
        var isMultiPane: Boolean = false,
    )
}