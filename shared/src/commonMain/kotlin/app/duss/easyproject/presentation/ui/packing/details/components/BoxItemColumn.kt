package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.BoxItem
import app.duss.easyproject.presentation.components.Constants

@Composable
fun BoxItemColumn(
    modifier: Modifier,
    list: List<BoxItem>,
    editState: Boolean,
    onItemUpdate: ((BoxItem) -> Unit)? = null,
    onItemDelete: ((BoxItem) -> Unit)? = null,
) {
    var listState by remember { mutableStateOf(list) }
    var index = -1
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
    ) {
        listState.map { item ->
            index ++
            BoxItemContent(
                editState = editState,
                item = item,
                onChange = { updated ->
                    listState = list.map {
                        if (it.id == updated.id) {
                            updated
                        } else {
                            it
                        }
                    }
                    onItemUpdate?.invoke(updated)
                },
                onDelete = {
                    listState = list.mapNotNull {
                        if (it.id == item.id) {
                            null
                        } else {
                            it
                        }
                    }
                    onItemDelete?.invoke(item)
                },
                isAllowedToChangeItem = true,
                brush = remember(index) {
                    val colorIndex = index % Constants.colorsOfListItems.size
                    Brush.linearGradient(Constants.colorsOfListItems[colorIndex])
                },
            )
        }
    }
}
