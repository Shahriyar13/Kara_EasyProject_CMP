package app.duss.easyproject.presentation.ui.quotation.list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.components.SimpleListItemContent
import app.duss.easyproject.presentation.components.SimplePagingVerticalGrid
import app.duss.easyproject.presentation.components.TopAppBarDocumentList
import app.duss.easyproject.presentation.ui.quotation.list.QuotationListComponent
import app.duss.easyproject.presentation.ui.quotation.list.store.QuotationListStore
import app.duss.easyproject.presentation.utils.toFormattedDate

@Composable
internal fun QuotationListContent(
    state: QuotationListStore.State,
    onEvent: (QuotationListStore.Intent) -> Unit,
    onOutput: (QuotationListComponent.Output) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBarDocumentList(
                title = "Quotation",
                onAddClicked = {
                    onEvent(QuotationListStore.Intent.AddNew)
                },
                onSearchValueChange = {
                    onEvent(QuotationListStore.Intent.UpdateSearchValue(it))
                }
            )
        },
    ) { paddingValues ->
        SimplePagingVerticalGrid(
            itemList = state.list,
            loadMoreItems = {
                if (state.list.isEmpty()) return@SimplePagingVerticalGrid
                val nextPage = state.page + 1
                onEvent(QuotationListStore.Intent.LoadByPage(page = nextPage))
            },
            isLoading = state.isLoading,
            isLastPageLoaded = state.isLastPageLoaded,
            modifier = Modifier.padding(paddingValues)
        ) { item, brush ->
            SimpleListItemContent(
                onClick = {
                    item.id?.let {
                        onEvent(QuotationListStore.Intent.Details(id = item.id))
                    }
                },
                title = (item.supplierEnquiry?.supplier?.name ?: "Unknown Customer") + " - " + ((item.supplierEnquiry?.takeIf { true }) ?: "No title"),
                subtitle = item.time?.toFormattedDate(),
                brush = brush
            )
        }
    }
}
