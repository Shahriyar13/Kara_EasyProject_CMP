package app.duss.easyproject.presentation.ui.packing.details.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.domain.entity.BoxItem
import app.duss.easyproject.domain.entity.BoxOfItem
import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.domain.params.FileAttachmentRequest
import app.duss.easyproject.domain.params.toDto
import app.duss.easyproject.domain.usecase.attachment.AttachmentDeleteUseCase
import app.duss.easyproject.domain.usecase.attachment.AttachmentUploadUseCase
import app.duss.easyproject.domain.usecase.packing.CreateUseCase
import app.duss.easyproject.domain.usecase.packing.DeleteUseCase
import app.duss.easyproject.domain.usecase.packing.GetByIdUseCase
import app.duss.easyproject.domain.usecase.packing.UpdateUseCase
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

internal class PackingDetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val id: Long?
) : KoinComponent {

    private val getByIdUseCase by inject<GetByIdUseCase>()
    private val createUseCase by inject<CreateUseCase>()
    private val updateUseCase by inject<UpdateUseCase>()
    private val deleteUseCase by inject<DeleteUseCase>()

    private val attachmentUploadUseCase by inject<AttachmentUploadUseCase>()
    private val attachmentDeleteUseCase by inject<AttachmentDeleteUseCase>()

    fun create(): PackingDetailsStore =
        object : PackingDetailsStore,
            Store<PackingDetailsStore.Intent, PackingDetailsStore.State, Nothing> by storeFactory.create(
                name = PackingDetailsStore::class.simpleName,
                initialState = PackingDetailsStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data object CheckChanges : Msg()
        data class Changing(val updated: Packing) : Msg()
        data object EditMode : Msg()
        data object ViewMode : Msg()
        data object InfoLoading : Msg()
        data class InfoLoaded(val item: Packing) : Msg()
        data class InfoFailed(val error: String?) : Msg()
        data object Updating : Msg()
        data class Updated(val item: Packing) : Msg()
        data class UpdateFailed(val error: String?) : Msg()
        data object Deleting : Msg()
        data object Deleted : Msg()
        data class DeleteFailed(val error: String?) : Msg()
        data object FileUploading : Msg()
        data class FileUploadFailed(val error: String?) : Msg()
        data object FileDeleting : Msg()
        data class FileDeleteFailed(val error: String?) : Msg()
        data class MoveQuotationItemToBox(val quotationItemId: Long, val oldBoxId: Long, val newBoxId: Long) : Msg()
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<PackingDetailsStore.Intent, Unit, PackingDetailsStore.State, Msg, Nothing>(
            appDispatchers.main
        ) {
        override fun executeAction(action: Unit, getState: () -> PackingDetailsStore.State) {
            loadById(id)
        }

        override fun executeIntent(
            intent: PackingDetailsStore.Intent,
            getState: () -> PackingDetailsStore.State
        ): Unit =
            when (intent) {
                is PackingDetailsStore.Intent.SaveState -> update(intent.request, intent.isChanged)
                is PackingDetailsStore.Intent.DeleteState -> delete(intent.id)
                is PackingDetailsStore.Intent.UploadFileState -> uploadFiles(
                    intent.id,
                    intent.request
                )

                is PackingDetailsStore.Intent.DeleteFileState -> deleteFile(
                    intent.id,
                    intent.fileId
                )

                PackingDetailsStore.Intent.EditState -> dispatch(Msg.EditMode)
                is PackingDetailsStore.Intent.EditingState -> {
                    dispatch(Msg.Changing(intent.updated))
                    dispatch(Msg.CheckChanges)

                }
                is PackingDetailsStore.Intent.MoveQuotationItemToBox -> {
                    dispatch( Msg.MoveQuotationItemToBox(
                        intent.quotationItemId,
                        intent.oldBoxId,
                        intent.newBoxId,
                    ))
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
                    dispatch(Msg.InfoLoaded(Packing()))
                }
            }
        }

        private var updateJob: Job? = null
        private fun update(request: Packing, isChanged: Boolean) {
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
                            customerEnquiryId = id,
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

    private object ReducerImpl : Reducer<PackingDetailsStore.State, Msg> {
        override fun PackingDetailsStore.State.reduce(msg: Msg): PackingDetailsStore.State =
            when (msg) {
                is Msg.InfoLoading -> copy(isLoading = true)
                is Msg.InfoLoaded -> copy(
                    item = msg.item.also {
                        it.quotationItemsNotInBoxes = it.findQuotationItemsNotInBoxes()
                        it.boxes = it.boxes + BoxOfItem()
                    },
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
                is Msg.MoveQuotationItemToBox -> {
                    val quotationItem = item!!.quotationItems.first { it.id == msg.quotationItemId }
                    var boxes = if (msg.newBoxId == -1000L) {
                        item.boxes + BoxOfItem(boxItems = listOf(BoxItem(quotationItem = quotationItem, quantity = quotationItem.quantity)))
                    } else {
                        item.boxes.map { box ->
                            if (box.uniqueId == msg.newBoxId) {
                                box.copy(
                                    boxItems = box.boxItems.plus(BoxItem(quotationItem = quotationItem, quantity = quotationItem.quantity))
                                )
                            } else {
                                box
                            }
                        }
                    }
                    if (!boxes.lastOrNull()?.boxItems.isNullOrEmpty()) {
                        boxes = boxes + BoxOfItem()
                    }
                    val newItem = item
                    newItem.boxes = boxes
                    newItem.quotationItemsNotInBoxes = newItem.findQuotationItemsNotInBoxes()
                    copy(item = newItem)
                }
            }
    }

}