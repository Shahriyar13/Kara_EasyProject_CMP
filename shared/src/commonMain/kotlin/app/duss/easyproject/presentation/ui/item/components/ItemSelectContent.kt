package app.duss.easyproject.presentation.ui.item.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.components.SimpleListItemContent
import app.duss.easyproject.presentation.components.SimplePagingVerticalGrid
import app.duss.easyproject.presentation.ui.item.ItemComponent
import app.duss.easyproject.presentation.ui.item.store.ItemStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ItemSelectContent(
    state: ItemStore.State,
    onEvent: (ItemStore.Intent) -> Unit,
    onOutput: (ItemComponent.Output) -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            onOutput(ItemComponent.Output.ItemsSelected(state.selected))
        },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier)
            Text("Select Items")
            IconButton(
                onClick = {
                    onOutput(ItemComponent.Output.ItemsSelected(state.selected))
                }
            ){
                Icon(Icons.Filled.Check, "done")
            }
        }
        Divider()
        SimplePagingVerticalGrid(
            itemList = state.list,
            loadMoreItems = {
                if (state.list.isEmpty()) return@SimplePagingVerticalGrid
                val nextPage = state.page + 1
                onEvent(ItemStore.Intent.LoadByPage(page = nextPage))
            },
            isLastPageLoaded = state.isLastPageLoaded,
            isLoading = state.isLoading,
        ) { item, brush ->
            SimpleListItemContent(
                onClick = {
                    if (state.selectMode) {
                        onEvent(ItemStore.Intent.UpdateSelected(item = item))
                    } else {
                        item.id?.let {
                            onEvent(ItemStore.Intent.Edit(id = item.id))
                        }
                    }
                },
                title = item.name,
                subtitle = "${item.unit?.let { "Unit: $it  | " }}${item.type?.let { "Type: $it  | " }}${item.modelNo?.let { "Model No.: $it  | " }}${item.hsCodeEu?.let { "EU HSCode: $it" }}",
                caption = item.note ?: "",
                brush = brush
            )
        }
    }
}
