package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.QuotationItem
import app.duss.easyproject.presentation.components.Constants
import app.duss.easyproject.presentation.components.draganddrop.DragSurface

@Composable
fun QuotationItemColumn(
    modifier: Modifier = Modifier,
    list: List<QuotationItem>,
    editState: Boolean,
    onItemUpdate: ((QuotationItem) -> Unit)? = null,
    onItemDelete: ((QuotationItem) -> Unit)? = null,
) {
    var index = -1
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        Text("Quotation Items:")
        list.map { item ->
            index ++
            DragSurface(modifier = Modifier, cardId = item.id!!, cardListId = 0) {
                QuotationItemContent(
                    item = item,
                    brush = remember(index) {
                        val colorIndex = index % Constants.colorsOfListItems.size
                        Brush.linearGradient(Constants.colorsOfListItems[colorIndex])
                    },
                )
            }
        }
    }
}
