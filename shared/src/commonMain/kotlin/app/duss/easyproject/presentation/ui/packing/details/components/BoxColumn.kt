package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.BoxOfItem
import app.duss.easyproject.presentation.components.Constants
import app.duss.easyproject.presentation.components.draganddrop.DragAndDropState
import app.duss.easyproject.presentation.components.draganddrop.DropSurface

@Composable
fun BoxColumn(
    boardState: DragAndDropState,
    modifier: Modifier = Modifier,
    list: List<BoxOfItem>,
    editState: Boolean,
    onItemUpdate: ((BoxOfItem) -> Unit)? = null,
    onItemDelete: ((BoxOfItem) -> Unit)? = null,
) {
    var listState by remember { mutableStateOf(list) }
    var index = -1
    Column(
        modifier = modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Packages:")

        listState.map { item ->
            DropSurface(
                listId = item.uniqueId,
                modifier = Modifier.fillMaxWidth(),
            ) { isInBound, _ ->
                BoxContent(
                    modifier = Modifier.background(
                        color = getDropSurfaceBgColor(isInBound, boardState.isDragging)
                    ).fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
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
                    brush = remember(++index) {
                        val colorIndex = index % Constants.colorsOfListItems.size
                        Brush.linearGradient(Constants.colorsOfListItems[colorIndex])
                    },
                )
            }
        }
    }
}

@Composable
fun getDropSurfaceBgColor(
    isInBound: Boolean,
    isDragging: Boolean
): Color {
    return if (isInBound && isDragging) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        Color.Transparent
    }
}