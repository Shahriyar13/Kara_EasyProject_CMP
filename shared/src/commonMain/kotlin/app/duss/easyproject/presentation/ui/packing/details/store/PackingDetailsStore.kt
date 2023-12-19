package app.duss.easyproject.presentation.ui.packing.details.store

import app.duss.easyproject.domain.entity.Packing
import com.arkivanov.mvikotlin.core.store.Store
import com.mohamedrejeb.calf.io.KmpFile

interface PackingDetailsStore: Store<PackingDetailsStore.Intent, PackingDetailsStore.State, Nothing> {

    sealed class Intent {
        data object EditState : Intent()
        data class MoveQuotationItemToBox(val quotationItemId: Long, val oldBoxId: Long, val newBoxId: Long) : Intent()
        data class EditingState(val updated: Packing) : Intent()
        data class SaveState(val request: Packing, val isChanged: Boolean) : Intent()
        data class UploadFileState(val id: Long, val request: List<KmpFile>) : Intent()
        data class DeleteFileState(val id: Long, val fileId: Long) : Intent()
        data class DeleteState(val id: Long) : Intent()
    }

    data class State(
        val inEditeMode: Boolean = false,
        val isLoading: Boolean = false,
        val isDeleted: Boolean = false,
        var isUpdated: Boolean = false,
        var isChanged: Boolean = false,
        val error: String? = null,
        val item: Packing? = null,
    )

}