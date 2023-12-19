package app.duss.easyproject.presentation.ui.packing.list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.components.SimpleListItemContent
import app.duss.easyproject.presentation.components.SimplePagingVerticalGrid
import app.duss.easyproject.presentation.components.TopAppBarDocumentList
import app.duss.easyproject.presentation.ui.packing.list.PackingListComponent
import app.duss.easyproject.presentation.ui.packing.list.store.PackingListStore
import app.duss.easyproject.presentation.utils.toFormattedDate

@Composable
internal fun PackingListContent(
    state: PackingListStore.State,
    onEvent: (PackingListStore.Intent) -> Unit,
    onOutput: (PackingListComponent.Output) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBarDocumentList(
                title = "Packing Lists",
                onAddClicked = {
                    onEvent(PackingListStore.Intent.AddNew)
                },
                onSearchValueChange = {
                    onEvent(PackingListStore.Intent.UpdateSearchValue(it))
                }
            )
        },
    ) { paddingValues ->
        SimplePagingVerticalGrid(
            itemList = state.list,
            loadMoreItems = {
                if (state.list.isEmpty()) return@SimplePagingVerticalGrid
                val nextPage = state.page + 1
                onEvent(PackingListStore.Intent.LoadByPage(page = nextPage))
            },
            isLoading = state.isLoading,
            isLastPageLoaded = state.isLastPageLoaded,
            modifier = Modifier.padding(paddingValues)
        ) { item, brush ->
            SimpleListItemContent(
                onClick = {
                    item.id?.let {
                        onEvent(PackingListStore.Intent.Details(id = item.id))
                    }
                },
                title = (item.code ?: "Unknown Packing"),
                subtitle = item.time?.toFormattedDate(),
                brush = brush
            )
        }
    }
}
