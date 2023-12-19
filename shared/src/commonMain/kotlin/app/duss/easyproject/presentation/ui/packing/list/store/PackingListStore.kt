package app.duss.easyproject.presentation.ui.packing.list.store

import app.duss.easyproject.domain.entity.Packing
import com.arkivanov.mvikotlin.core.store.Store

interface PackingListStore: Store<PackingListStore.Intent, PackingListStore.State, Nothing> {

    sealed class Intent {
        data class LoadByPage(val page: Int): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
        data object AddNew: Intent()
        data object DetailsDone: Intent()
        data class DetailsChanged(val item: Packing): Intent()
        data class DetailsDeleted(val deletedId: Long): Intent()
        data class Details(val id: Long): Intent()
        data object Refresh: Intent()

    }

    data class State(
        var page: Int = 0,
        var id: Long? = null,
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val list: List<Packing> = emptyList(),
        val searchValue: String = "",
    )
}