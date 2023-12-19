package app.duss.easyproject.presentation.ui.quotation.details.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.domain.entity.Quotation
import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.params.FileAttachmentRequest
import app.duss.easyproject.domain.params.toDto
import app.duss.easyproject.domain.usecase.attachment.AttachmentDeleteUseCase
import app.duss.easyproject.domain.usecase.attachment.AttachmentUploadUseCase
import app.duss.easyproject.domain.usecase.quotation.CreateUseCase
import app.duss.easyproject.domain.usecase.quotation.DeleteUseCase
import app.duss.easyproject.domain.usecase.quotation.GetByIdUseCase
import app.duss.easyproject.domain.usecase.quotation.UpdateUseCase
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

internal class QuotationDetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val id: Long?
) : KoinComponent {

    private val getByIdUseCase by inject<GetByIdUseCase>()
    private val createUseCase by inject<CreateUseCase>()
    private val updateUseCase by inject<UpdateUseCase>()
    private val deleteUseCase by inject<DeleteUseCase>()

    private val attachmentUploadUseCase by inject<AttachmentUploadUseCase>()
    private val attachmentDeleteUseCase by inject<AttachmentDeleteUseCase>()

    fun create(): QuotationDetailsStore =
        object : QuotationDetailsStore,
            Store<QuotationDetailsStore.Intent, QuotationDetailsStore.State, Nothing> by storeFactory.create(
                name = QuotationDetailsStore::class.simpleName,
                initialState = QuotationDetailsStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data object CheckChanges : Msg()
        data class Changing(val updated: Quotation) : Msg()
        data object EditMode : Msg()
        data object ViewMode : Msg()
        data object InfoLoading : Msg()
        data class InfoLoaded(val item: Quotation) : Msg()
        data class InfoFailed(val error: String?) : Msg()
        data object Updating : Msg()
        data class Updated(val item: Quotation) : Msg()
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
        CoroutineExecutor<QuotationDetailsStore.Intent, Unit, QuotationDetailsStore.State, Msg, Nothing>(
            appDispatchers.main
        ) {
        override fun executeAction(action: Unit, getState: () -> QuotationDetailsStore.State) {
            loadById(id)
        }

        override fun executeIntent(
            intent: QuotationDetailsStore.Intent,
            getState: () -> QuotationDetailsStore.State
        ): Unit =
            when (intent) {
                is QuotationDetailsStore.Intent.SaveState -> update(intent.request, intent.isChanged)
                is QuotationDetailsStore.Intent.DeleteState -> delete(intent.id)
                is QuotationDetailsStore.Intent.UploadFileState -> uploadFiles(
                    intent.id,
                    intent.request
                )

                is QuotationDetailsStore.Intent.DeleteFileState -> deleteFile(
                    intent.id,
                    intent.fileId
                )

                QuotationDetailsStore.Intent.EditState -> dispatch(Msg.EditMode)
                is QuotationDetailsStore.Intent.EditingState -> {
                    dispatch(Msg.Changing(intent.updated))
                    dispatch(Msg.CheckChanges)

                }
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
                    dispatch(Msg.InfoLoaded(Quotation(null)))
                }
            }
        }

        private var updateJob: Job? = null
        private fun update(request: Quotation, isChanged: Boolean) {
            if ((request.id ?: -1) > 0 && !isChanged) return dispatch(Msg.Updated(request))
            if (updateJob?.isActive == true) return

            updateJob = scope.launch {
                if ((request.id ?: -1) > 0) {
                    dispatch(Msg.Updating)
                    updateUseCase
                        .execute(request.toDto())
                        .onSuccess { item -> dispatch(Msg.Updated(item)) }
                        .onFailure { e -> dispatch(Msg.UpdateFailed(e.message)) }
                } else {
                    createUseCase
                        .execute(request.toDto())
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
                            quotationId = id,
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

    private object ReducerImpl : Reducer<QuotationDetailsStore.State, Msg> {
        override fun QuotationDetailsStore.State.reduce(msg: Msg): QuotationDetailsStore.State =
            when (msg) {
                is Msg.InfoLoading -> copy(isLoading = true)
                is Msg.InfoLoaded -> copy(
                    item = msg.item,
                    inEditeMode = (msg.item.id ?: -1) < 0,
                    isLoading = false
                )

                is Msg.InfoFailed -> copy(error = msg.error, isLoading = false)
                is Msg.Updating -> copy(isLoading = true)
                is Msg.Updated -> copy(
                    item = msg.item,
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
                is Msg.Changing -> copy(
                    item = msg.updated,
                )
                Msg.CheckChanges -> copy(isChanged = true)
            }
    }

}