package app.duss.easyproject.presentation.ui.ce.list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.components.SimpleListItemContent
import app.duss.easyproject.presentation.components.SimplePagingVerticalGrid
import app.duss.easyproject.presentation.components.TopAppBarDocumentList
import app.duss.easyproject.presentation.ui.ce.list.CEListComponent
import app.duss.easyproject.presentation.ui.ce.list.store.CEListStore
import app.duss.easyproject.presentation.utils.toFormattedDate

@Composable
internal fun CEListContent(
    state: CEListStore.State,
    onEvent: (CEListStore.Intent) -> Unit,
    onOutput: (CEListComponent.Output) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBarDocumentList(
                title = "Customer Enquiries",
                onAddClicked = {
                    onEvent(CEListStore.Intent.AddNew)
                },
                onSearchValueChange = {
                    onEvent(CEListStore.Intent.UpdateSearchValue(it))
                }
            )
        },
    ) { paddingValues ->
        SimplePagingVerticalGrid(
            itemList = state.list,
            loadMoreItems = {
                if (state.list.isEmpty()) return@SimplePagingVerticalGrid
                val nextPage = state.page + 1
                onEvent(CEListStore.Intent.LoadByPage(page = nextPage))
            },
            isLoading = state.isLoading,
            modifier = Modifier.padding(paddingValues)
        ) { item, brush ->
            SimpleListItemContent(
                onClick = {
                    item.id?.let {
                        onEvent(CEListStore.Intent.Details(id = item.id))
                    }
                },
                title = (item.customerBuyer?.name ?: "Unknown Customer") + " - " + ((item.title?.takeIf { it.isNotBlank() }) ?: "No title"),
                subtitle = item.time?.toFormattedDate(),
                brush = brush
            )
        }
    }
}
