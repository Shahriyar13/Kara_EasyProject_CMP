package app.duss.easyproject.presentation.ui.item.store

import app.duss.easyproject.domain.entity.Item
import com.arkivanov.mvikotlin.core.store.Store

interface ItemStore: Store<ItemStore.Intent, ItemStore.State, Nothing> {

    sealed class Intent {
        data class LoadByPage(val page: Int): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
        data object New: Intent()
        data object EditDone: Intent()
        data class Update(val item: Item): Intent()
        data class UpdateSelected(val item: Item): Intent()
        data class Delete(val deletedId: Long): Intent()
        data class Edit(val id: Long): Intent()
    }

    data class State(
        var page: Int = 0,
        var id: Long? = null,
        val selectMode: Boolean = false,
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val list: List<Item> = emptyList(),
        val selected: List<Item> = emptyList(),
        val selectingDone: Boolean= false,
        val searchValue: String = "",
    )

}