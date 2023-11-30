package app.duss.easyproject.presentation.ui.project.pane.store

import com.arkivanov.mvikotlin.core.store.Store

interface ProjectStore: Store<ProjectStore.Intent, ProjectStore.State, Nothing> {

    sealed class Intent {
        data object NewSelected : Intent()

        data class DetailsSelected(val id: Long) : Intent()

        data object DetailsEditDone : Intent()
    }

    data class State(
        val isMultiPane: Boolean = false,
        val itemId: Long? = null, //null means no item selected and -1 means new item creation
    )
}