package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.BoxOfItem

@Composable
fun BoxContent(
    modifier: Modifier = Modifier,
    item: BoxOfItem,
    onChange: (BoxOfItem) -> Unit,
    editState: Boolean = false,
    brush: Brush,
    onDelete: () -> Unit,
) {
    val itemState by remember { mutableStateOf(item) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            draggedElevation = 0.dp,
            disabledElevation = 0.dp,
        ),
        modifier = modifier
            .background(brush, shape = ShapeDefaults.Small, alpha = 1F)
            .wrapContentHeight()
            .fillMaxWidth(),
    ) {
        Text(
            text = "Box: ${itemState.code ?: itemState.id ?: "New Created"}",
            modifier = Modifier.padding(start = 16.dp)
        )
        BoxItemColumn(
            modifier = Modifier.padding(16.dp),
            list = itemState.boxItems,
            editState = editState,
            onItemUpdate = { updatedItem ->
                itemState.boxItems = itemState.boxItems.map {
                    if (updatedItem.uniqueId == it.uniqueId) {
                        updatedItem
                    } else {
                        it
                    }
                }
                onChange(itemState)
            },
            onItemDelete = { deletedItem ->
                itemState.boxItems = itemState.boxItems.mapNotNull {
                    if (deletedItem.uniqueId == it.uniqueId) {
                        null
                    } else {
                        it
                    }
                }
                onChange(itemState)
            }
        )
    }
}