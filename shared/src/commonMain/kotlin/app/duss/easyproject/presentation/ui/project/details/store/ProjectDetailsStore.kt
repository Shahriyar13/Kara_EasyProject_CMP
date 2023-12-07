package app.duss.easyproject.presentation.ui.project.details.store

import app.duss.easyproject.domain.entity.Project
import com.arkivanov.mvikotlin.core.store.Store
import com.mohamedrejeb.calf.io.KmpFile

interface ProjectDetailsStore: Store<ProjectDetailsStore.Intent, ProjectDetailsStore.State, Nothing> {

    sealed class Intent {
        data object EditState : Intent()
        data class SaveState(val request: Project) : Intent()
        data class UploadFileState(val projectId: Long, val request: List<KmpFile>) : Intent()
        data class DeleteFileState(val projectId: Long, val fileId: Long) : Intent()
        data class DeleteState(val id: Long) : Intent()
    }

    data class State(
        val inEditeMode: Boolean = false,
        val isLoading: Boolean = false,
        val isDeleted: Boolean = false,
        val error: String? = null,
        val projectInfo: Project? = null
    )

}