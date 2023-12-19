package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.BoxOfItem
import app.duss.easyproject.domain.entity.CustomerEnquiryItem
import app.duss.easyproject.presentation.components.Constants
import app.duss.easyproject.presentation.components.draganddrop.DragAndDropState
import app.duss.easyproject.presentation.components.draganddrop.DropSurface

@Composable
fun BoxColumn(
    boardState: DragAndDropState,
    modifier: Modifier = Modifier,
    list: List<BoxOfItem>,
    editState: Boolean,
    onItemUpdate: ((CustomerEnquiryItem) -> Unit)? = null,
    onItemDelete: ((CustomerEnquiryItem) -> Unit)? = null,
) {
    var mostNegativeBoxId = 0L
    var index = -1
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Packages:")

        list.map { item ->
            DropSurface(
                listId = item.id ?: --mostNegativeBoxId,
                modifier = Modifier.fillMaxWidth(),
            ) { isInBound, _ ->
                BoxContent(
                    modifier = Modifier.background(
                        color = getDropSurfaceBgColor(isInBound, boardState.isDragging)
                    ).fillMaxWidth()
                        .requiredHeight(200.dp)
                        .padding(8.dp),
                    editState = editState,
                    item = item,
                    onChange = {
//                    onItemUpdate?.invoke(it)
                    },
                    onDelete = {
//                    onItemDelete?.invoke(item)
                    },
                    brush = remember(++index) {
                        val colorIndex = index % Constants.colorsOfListItems.size
                        Brush.linearGradient(Constants.colorsOfListItems[colorIndex])
                    },
                )
            }
        }
        DropSurface(
            listId = -1000L,
            modifier = Modifier.fillMaxWidth(),
        ) { isInBound, offset ->
            BoxContent(
                modifier = Modifier.background(
                    color = getDropSurfaceBgColor(isInBound, boardState.isDragging)
                ).fillMaxWidth()
                    .offset(x = offset.x.dp, y = offset.y.dp)
                    .padding(8.dp),
                editState = editState,
                item = BoxOfItem(),
                onChange = {
//                    onItemUpdate?.invoke(it)
                },
                onDelete = {
//                    onItemDelete?.invoke(item)
                },
                brush = remember(++index) {
                    val colorIndex = index % Constants.colorsOfListItems.size
                    Brush.linearGradient(Constants.colorsOfListItems[colorIndex])
                },
            )
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