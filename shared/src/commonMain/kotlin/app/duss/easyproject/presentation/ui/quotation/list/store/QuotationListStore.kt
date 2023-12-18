package app.duss.easyproject.presentation.ui.quotation.list.store

import app.duss.easyproject.domain.entity.Quotation
import com.arkivanov.mvikotlin.core.store.Store

interface QuotationListStore: Store<QuotationListStore.Intent, QuotationListStore.State, Nothing> {

    sealed class Intent {
        data class LoadByPage(val page: Int): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
        data object AddNew: Intent()
        data object DetailsDone: Intent()
        data class DetailsChanged(val item: Quotation): Intent()
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
        val list: List<Quotation> = emptyList(),
        val searchValue: String = "",
    )
}