package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import app.duss.easyproject.domain.entity.BoxItem
import app.duss.easyproject.presentation.components.BasicEditableText
import app.duss.easyproject.presentation.components.QuantityChanger

@Composable
fun BoxItemContent(
    modifier: Modifier = Modifier,
    item: BoxItem,
    onChange: (BoxItem) -> Unit,
    editState: Boolean = false,
    isAllowedToChangeItem: Boolean = false,
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
        modifier = modifier.width(200.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(brush = brush, alpha = .4f)
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1F),
            ) {
                BasicEditableText(
                    label = "Name",
                    readOnly = true,
                    value = itemState.quotationItem.customerEnquiryItem.item.name,
                    onValueChange = {
                        itemState.quotationItem.customerEnquiryItem.item.name = it ?: ""
                        onChange(itemState)
                    },
                    singleLine = true,
                )

                QuantityChanger(
                    max = itemState.quotationItem.quantity,
                    quantity = itemState.quantity,
                    editState = editState,
                    unit = itemState.quotationItem.customerEnquiryItem.item.unit,
                    onChange = {
                        itemState.quantity = it
                        onChange(itemState)
                    }
                )
            }
        }
    }
}