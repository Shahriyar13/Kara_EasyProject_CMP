package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
        modifier = modifier.requiredHeight(200.dp).fillMaxWidth()
    ) {
        BoxItemColumn(
            modifier = Modifier.padding(50.dp),
            item.boxItems,
            editState,
            onItemUpdate = { updatedItem ->

            },
            onItemDelete = { deletedItem ->

            }
        )
    }
}