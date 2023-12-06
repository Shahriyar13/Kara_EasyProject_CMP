package app.duss.easyproject.presentation.ui.project.details.store

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.FileAttachmentUpdateRequest
import app.duss.easyproject.domain.params.toCreateRequest
import app.duss.easyproject.domain.params.toUpdateRequest
import app.duss.easyproject.domain.usecase.attachment.AttachmentDeleteUseCase
import app.duss.easyproject.domain.usecase.attachment.AttachmentUploadUseCase
import app.duss.easyproject.domain.usecase.project.ProjectCreateUseCase
import app.duss.easyproject.domain.usecase.project.ProjectDeleteUseCase
import app.duss.easyproject.domain.usecase.project.ProjectGetByIdUseCase
import app.duss.easyproject.domain.usecase.project.ProjectUpdateUseCase
import app.duss.easyproject.utils.appDispatchers
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ProjectDetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val projectId: Long?
) : KoinComponent {

    private val projectGetByIdUseCase by inject<ProjectGetByIdUseCase>()
    private val projectCreateUseCase by inject<ProjectCreateUseCase>()
    private val projectUpdateUseCase by inject<ProjectUpdateUseCase>()
    private val projectDeleteUseCase by inject<ProjectDeleteUseCase>()

    private val projectFileUploadUseCase by inject<AttachmentUploadUseCase>()
    private val projectFileDeleteUseCase by inject<AttachmentDeleteUseCase>()

    fun create(): ProjectDetailsStore =
        object : ProjectDetailsStore,
            Store<ProjectDetailsStore.Intent, ProjectDetailsStore.State, Nothing> by storeFactory.create(
                name = ProjectDetailsStore::class.simpleName,
                initialState = ProjectDetailsStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data object InfoLoading : Msg()
        data class InfoLoaded(val project: Project) : Msg()
        data class InfoFailed(val error: String?) : Msg()
        data object Updating : Msg()
        data class Updated(val project: Project) : Msg()
        data class UpdateFailed(val error: String?) : Msg()
        data object Deleting : Msg()
        data object Deleted : Msg()
        data class DeleteFailed(val error: String?) : Msg()
        data object FileUploading : Msg()
        data class FileUploadFailed(val error: String?) : Msg()
        data object FileDeleting : Msg()
        data class FileDeleteFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ProjectDetailsStore.Intent, Unit, ProjectDetailsStore.State, Msg, Nothing>(
            appDispatchers.main
        ) {
        override fun executeAction(action: Unit, getState: () -> ProjectDetailsStore.State) {
            loadProjectById(projectId)
        }

        override fun executeIntent(
            intent: ProjectDetailsStore.Intent,
            getState: () -> ProjectDetailsStore.State
        ): Unit =
            when (intent) {
                is ProjectDetailsStore.Intent.SaveState -> updateProject(intent.request)
                is ProjectDetailsStore.Intent.DeleteState -> deleteProject(intent.id)
                is ProjectDetailsStore.Intent.UploadFileState -> uploadFiles(intent.projectId, intent.request)
                is ProjectDetailsStore.Intent.DeleteFileState -> deleteFile(intent.projectId, intent.fileId)
                ProjectDetailsStore.Intent.EditState -> Unit
            }

        private var loadProjectByIdJob: Job? = null
        private fun loadProjectById(id: Long?) {
            if (loadProjectByIdJob?.isActive == true) return

            loadProjectByIdJob = scope.launch {
                dispatch(Msg.InfoLoading)

                projectGetByIdUseCase
                    .execute(id)
                    .onSuccess { project ->
                        dispatch(Msg.InfoLoaded(project))
                    }
                    .onFailure { e ->
                        dispatch(Msg.InfoFailed(e.message))
                    }
            }
        }

        private var updateProjectJob: Job? = null
        private fun updateProject(request: Project) {
            if (updateProjectJob?.isActive == true) return

            updateProjectJob = scope.launch {
                if ((request.id ?: -1) > 0) {
                    dispatch(Msg.Updating)
                    projectUpdateUseCase
                        .execute(request.toUpdateRequest())
                        .onSuccess { project -> dispatch(Msg.Updated(project)) }
                        .onFailure { e -> dispatch(Msg.UpdateFailed(e.message)) }
                } else {
                    projectCreateUseCase
                        .execute(request.toCreateRequest())
                        .onSuccess { project -> dispatch(Msg.Updated(project)) }
                        .onFailure { e -> dispatch(Msg.UpdateFailed(e.message)) }
                }
            }
        }

        private var deleteProjectJob: Job? = null
        private fun deleteProject(id: Long) {
            if (deleteProjectJob?.isActive == true) return
            deleteProjectJob = scope.launch {
                dispatch(Msg.Deleting)

                projectDeleteUseCase
                    .execute(id)
                    .onSuccess { dispatch(Msg.Deleted) }
                    .onFailure { e -> dispatch(Msg.DeleteFailed(e.message)) }
            }
        }

        private var uploadFilesJob: Job? = null
        private fun uploadFiles(projectId: Long, request: List<MPFile<*>>) {
            if (uploadFilesJob?.isActive == true) return

            uploadFilesJob = scope.launch {
                    dispatch(Msg.FileUploading)
                projectFileUploadUseCase
                        .execute(FileAttachmentUpdateRequest(
                            projectId = projectId,
                            files = request,
                        ))
                        .onSuccess { loadProjectById(projectId) }
                        .onFailure { e -> dispatch(Msg.FileUploadFailed(e.message)) }
                }

        }

        private var deleteFileJob: Job? = null
        private fun deleteFile(projectId: Long, fileId: Long) {
            if (deleteFileJob?.isActive == true) return

            deleteFileJob = scope.launch {
                    dispatch(Msg.FileDeleting)
                projectFileDeleteUseCase
                        .execute(fileId)
                        .onSuccess { loadProjectById(projectId) }
                        .onFailure { e -> dispatch(Msg.FileDeleteFailed(e.message)) }
                }

        }
    }

    private object ReducerImpl : Reducer<ProjectDetailsStore.State, Msg> {
        override fun ProjectDetailsStore.State.reduce(msg: Msg): ProjectDetailsStore.State =
            when (msg) {
                is Msg.InfoLoading -> copy(isLoading = true)
                is Msg.InfoLoaded -> copy(
                    projectInfo = msg.project,
                    inEditeMode = (msg.project.id ?: -1) < 0,
                    isLoading = false
                )

                is Msg.InfoFailed -> copy(error = msg.error, isLoading = false)
                is Msg.Updating -> copy(isLoading = true)
                is Msg.Updated -> copy(
                    projectInfo = msg.project,
                    isLoading = false,
                    inEditeMode = false
                )

                is Msg.UpdateFailed -> copy(isLoading = false, error = msg.error)


                is Msg.DeleteFailed -> copy(isLoading = false, error = msg.error)
                Msg.Deleted -> copy(
                    isDeleted = true,
                    isLoading = false,
                    inEditeMode = false
                )
                Msg.Deleting -> copy(isLoading = true)
                is Msg.FileDeleteFailed -> copy(isLoading = false, error = msg.error)

                Msg.FileDeleting -> copy(isLoading = true)
                is Msg.FileUploadFailed -> copy(isLoading = false, error = msg.error)

                Msg.FileUploading -> copy(isLoading = true)
            }
    }

}