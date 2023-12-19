package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.BoxItem
import app.duss.easyproject.domain.entity.CustomerEnquiryItem
import app.duss.easyproject.presentation.components.Constants

@Composable
fun BoxItemColumn(
    modifier: Modifier,
    list: List<BoxItem>,
    editState: Boolean,
    onItemUpdate: ((CustomerEnquiryItem) -> Unit)? = null,
    onItemDelete: ((CustomerEnquiryItem) -> Unit)? = null,
) {
    var index = -1
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        list.map { item ->
            index ++
            BoxItemContent(
                editState = editState,
                item = item,
                onChange = {
//                    onItemUpdate?.invoke(it)
                },
                onDelete = {
//                    onItemDelete?.invoke(item)
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
