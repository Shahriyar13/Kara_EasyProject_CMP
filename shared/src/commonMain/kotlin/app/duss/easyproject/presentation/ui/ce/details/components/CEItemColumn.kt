package app.duss.easyproject.presentation.ui.ce.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.CustomerEnquiryItem
import app.duss.easyproject.presentation.components.Constants

@Composable
fun CEItemColumn(
    list: List<CustomerEnquiryItem>,
    editState: Boolean,
    onItemUpdate: ((CustomerEnquiryItem) -> Unit)? = null,
    onItemDelete: ((CustomerEnquiryItem) -> Unit)? = null,
) {
    var index = -1
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        list.map { ceItem ->
            index ++
            CEItemContent(
                editState = editState,
                customerEnquiryItem = ceItem,
                onChange = {
                    onItemUpdate?.invoke(it)
                },
                onDelete = {
                    onItemDelete?.invoke(ceItem)
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
