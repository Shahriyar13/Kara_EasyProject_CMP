package app.duss.easyproject.presentation.ui.ce.details.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.params.FileAttachmentRequest
import app.duss.easyproject.domain.usecase.attachment.AttachmentDeleteUseCase
import app.duss.easyproject.domain.usecase.attachment.AttachmentUploadUseCase
import app.duss.easyproject.domain.usecase.ce.CreateUseCase
import app.duss.easyproject.domain.usecase.ce.DeleteUseCase
import app.duss.easyproject.domain.usecase.ce.GetByIdUseCase
import app.duss.easyproject.domain.usecase.ce.UpdateUseCase
import app.duss.easyproject.presentation.forms.CustomerEnquiryForm
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.mohamedrejeb.calf.io.KmpFile
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class CEDetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val id: Long?
) : KoinComponent {

    private val getByIdUseCase by inject<GetByIdUseCase>()
    private val getNewUseCase by inject<GetNewUseCase>()
    private val createUseCase by inject<CreateUseCase>()
    private val updateUseCase by inject<UpdateUseCase>()
    private val deleteUseCase by inject<DeleteUseCase>()

    private val attachmentUploadUseCase by inject<AttachmentUploadUseCase>()
    private val attachmentDeleteUseCase by inject<AttachmentDeleteUseCase>()

    fun create(): CEDetailsStore =
        object : CEDetailsStore,
            Store<CEDetailsStore.Intent, CEDetailsStore.State, Nothing> by storeFactory.create(
                name = CEDetailsStore::class.simpleName,
                initialState = CEDetailsStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data object Changing : Msg()
        data object EditMode : Msg()
        data object ViewMode : Msg()
        data object InfoLoading : Msg()
        data class InfoLoaded(val item: CustomerEnquiry) : Msg()
        data class InfoFailed(val error: String?) : Msg()
        data object Updating : Msg()
        data class Updated(val item: CustomerEnquiry) : Msg()
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
        CoroutineExecutor<CEDetailsStore.Intent, Unit, CEDetailsStore.State, Msg, Nothing>(
            appDispatchers.main
        ) {
        override fun executeAction(action: Unit, getState: () -> CEDetailsStore.State) {
            loadById(id)
        }

        override fun executeIntent(
            intent: CEDetailsStore.Intent,
            getState: () -> CEDetailsStore.State
        ): Unit =
            when (intent) {
                is CEDetailsStore.Intent.SaveState -> update(intent.request, intent.isChanged)
                is CEDetailsStore.Intent.DeleteState -> delete(intent.id)
                is CEDetailsStore.Intent.UploadFileState -> uploadFiles(
                    intent.id,
                    intent.request
                )

                is CEDetailsStore.Intent.DeleteFileState -> deleteFile(
                    intent.id,
                    intent.fileId
                )

                CEDetailsStore.Intent.EditState -> dispatch(Msg.EditMode)
                CEDetailsStore.Intent.EditingState -> dispatch(Msg.Changing)
            }

        private var loadByIdJob: Job? = null
        private fun loadById(id: Long?) {
            if (loadByIdJob?.isActive == true) return

            loadByIdJob = scope.launch {
                dispatch(Msg.InfoLoading)

                if ((id ?: -1) > 0) {
                    getByIdUseCase
                        .execute(id!!)
                        .onSuccess { item ->
                            dispatch(Msg.InfoLoaded(item))
                        }
                        .onFailure { e ->
                            dispatch(Msg.InfoFailed(e.message))
                        }
                } else {
                    getNewUseCase
                        .execute(Unit)
                        .onSuccess { item ->
                            dispatch(Msg.InfoLoaded(item))
                        }
                        .onFailure { e ->
                            dispatch(Msg.InfoFailed(e.message))
                        }
                }

            }
        }

        private var updateJob: Job? = null
        private fun update(request: CustomerEnquiryForm, isChanged: Boolean) {
            if ((request.origin.id ?: -1) > 0 && !isChanged) return dispatch(Msg.Updated(request.origin))
            if (updateJob?.isActive == true) return

            updateJob = scope.launch {
                if ((request.origin.id ?: -1) > 0) {
                    dispatch(Msg.Updating)
                    updateUseCase
                        .execute(request.toRequestDto())
                        .onSuccess { item -> dispatch(Msg.Updated(item)) }
                        .onFailure { e -> dispatch(Msg.UpdateFailed(e.message)) }
                } else {
                    createUseCase
                        .execute(request.toRequestDto())
                        .onSuccess { item -> dispatch(Msg.Updated(item)) }
                        .onFailure { e -> dispatch(Msg.UpdateFailed(e.message)) }
                }
            }
        }

        private var deleteJob: Job? = null
        private fun delete(id: Long) {
            if (deleteJob?.isActive == true) return
            deleteJob = scope.launch {
                dispatch(Msg.Deleting)
                deleteUseCase
                    .execute(id)
                    .onSuccess { dispatch(Msg.Deleted) }
                    .onFailure { e -> dispatch(Msg.DeleteFailed(e.message)) }
            }
        }

        private var uploadFilesJob: Job? = null
        private fun uploadFiles(id: Long, request: List<KmpFile>) {
            if (uploadFilesJob?.isActive == true) return

            uploadFilesJob = scope.launch {
                dispatch(Msg.FileUploading)
                attachmentUploadUseCase
                    .execute(
                        FileAttachmentRequest(
                            projectId = id,
                            files = request,
                        )
                    )
                    .onSuccess { loadById(id) }
                    .onFailure { e -> dispatch(Msg.FileUploadFailed(e.message)) }
            }

        }

        private var deleteFileJob: Job? = null
        private fun deleteFile(id: Long, fileId: Long) {
            if (deleteFileJob?.isActive == true) return

            deleteFileJob = scope.launch {
                dispatch(Msg.FileDeleting)
                attachmentDeleteUseCase
                    .execute(fileId)
                    .onSuccess { loadById(id) }
                    .onFailure { e -> dispatch(Msg.FileDeleteFailed(e.message)) }
            }

        }
    }

    private object ReducerImpl : Reducer<CEDetailsStore.State, Msg> {
        override fun CEDetailsStore.State.reduce(msg: Msg): CEDetailsStore.State =
            when (msg) {
                is Msg.InfoLoading -> copy(isLoading = true)
                is Msg.InfoLoaded -> copy(
                    form = CustomerEnquiryForm(msg.item),
                    inEditeMode = (msg.item.id ?: -1) < 0,
                    isLoading = false
                )

                is Msg.InfoFailed -> copy(error = msg.error, isLoading = false)
                is Msg.Updating -> copy(isLoading = true)
                is Msg.Updated -> copy(
                    form = CustomerEnquiryForm(msg.item),
                    isChanged = false,
                    isUpdated = true,
                    isLoading = false,
                    inEditeMode = false,
                )

                is Msg.UpdateFailed -> copy(isLoading = false, error = msg.error)


                is Msg.DeleteFailed -> copy(isLoading = false, error = msg.error)
                Msg.Deleted -> copy(isDeleted = true)

                Msg.Deleting -> copy(isLoading = true)
                is Msg.FileDeleteFailed -> copy(isLoading = false, error = msg.error)

                Msg.FileDeleting -> copy(isLoading = true)
                is Msg.FileUploadFailed -> copy(isLoading = false, error = msg.error)

                Msg.FileUploading -> copy(isLoading = true)
                Msg.EditMode -> copy(inEditeMode = true)
                Msg.ViewMode -> copy(inEditeMode = false)
                Msg.Changing -> copy(isChanged = form?.hasUnsavedChanges() ?: false)
            }
    }

}