package app.duss.easyproject.presentation.ui.ce.list.store

import app.duss.easyproject.domain.entity.CustomerEnquiry
import com.arkivanov.mvikotlin.core.store.Store

interface CEListStore: Store<CEListStore.Intent, CEListStore.State, Nothing> {

    sealed class Intent {
        data class LoadByPage(val page: Int): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
        data object AddNew: Intent()
        data object DetailsDone: Intent()
        data class DetailsChanged(val item: CustomerEnquiry): Intent()
        data class DetailsDeleted(val deletedId: Long): Intent()
        data class Details(val id: Long): Intent()
    }

    data class State(
        var page: Int = 0,
        var id: Long? = null,
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val list: List<CustomerEnquiry> = emptyList(),
        val searchValue: String = "",
    )
}