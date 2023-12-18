package app.duss.easyproject.presentation.ui.quotation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.QuotationItem
import app.duss.easyproject.presentation.components.Constants

@Composable
fun QuotationItemColumn(
    list: List<QuotationItem>,
    editState: Boolean,
    onItemUpdate: ((QuotationItem) -> Unit)? = null,
    onItemDelete: ((QuotationItem) -> Unit)? = null,
) {
    var index = -1
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        list.map { item ->
            index ++
            QuotationItemContent(
                editState = editState,
                item = item,
                onChange = {
                    onItemUpdate?.invoke(it)
                },
                onDelete = {
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
