package app.duss.easyproject.presentation.ui.quotation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.QuotationItem
import app.duss.easyproject.presentation.components.BasicEditableText
import app.duss.easyproject.presentation.ui.ce.details.components.QuantityChanger

@Composable
fun QuotationItemContent(
    modifier: Modifier = Modifier,
    item: QuotationItem,
    onChange: (QuotationItem) -> Unit,
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
        modifier = modifier
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
                    readOnly = !editState || !isAllowedToChangeItem,
                    value = itemState.customerEnquiryItem.item.name,
                    onValueChange = {
                        itemState.customerEnquiryItem.item.name = it ?: ""
                        onChange(itemState)
                    },
                    singleLine = true,
                )

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicEditableText(
                        label = "Type",
                        readOnly = !editState || !isAllowedToChangeItem,
                        value = itemState.customerEnquiryItem.item.type ?: "",
                        onValueChange = {
                            itemState.customerEnquiryItem.item.type = it
                            onChange(itemState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                    BasicEditableText(
                        label = "Model No",
                        readOnly = !editState || !isAllowedToChangeItem,
                        value = itemState.customerEnquiryItem.item.modelNo ?: "",
                        onValueChange = {
                            itemState.customerEnquiryItem.item.modelNo = it
                            onChange(itemState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                }

                BasicEditableText(
                    label = "Description",
                    readOnly = !editState,
                    value = itemState.note,
                    onValueChange = {
                        itemState.note = it ?: ""
                        onChange(itemState)
                    },
                    singleLine = false,
                    minLines = 4,
                    maxLines = 4,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                IconButton(
                    enabled = editState,
                    modifier = Modifier.offset(x = 8.dp),
                    onClick = { if (editState) onDelete() },
                ) {
                    if (editState) Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Delete"
                    )
                    else Spacer(Modifier.size(24.dp))
                }

                QuantityChanger(
                    quantity = itemState.customerEnquiryItem.quantity,
                    unit = itemState.customerEnquiryItem.item.unit ?: "Unit",
                    editState = false,
                ) {
                    itemState.quantity = it
                    onChange(itemState)
                }

                Spacer(Modifier.height(48.dp))

            }
        }
    }
}