//package app.duss.easyproject.presentation.ui.ce.details.components
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.IntrinsicSize
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import app.duss.easyproject.domain.entity.CustomerEnquiryItem
//import app.duss.easyproject.presentation.components.EditableText
//
//@Composable
//fun CEItemContent(
//    modifier: Modifier = Modifier,
//    customerEnquiryItem: CustomerEnquiryItem,
//    onChange: (CustomerEnquiryItem) -> Unit,
//    editState: Boolean = false,
//    isAllowedToChangeItem: Boolean = false,
//    brush: Brush,
//    onDelete: () -> Unit,
//) {
//    val customerEnquiryState by remember { mutableStateOf(customerEnquiryItem) }
//    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = Color.Transparent,
//            contentColor = MaterialTheme.colorScheme.onBackground,
//        ),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 0.dp,
//            pressedElevation = 0.dp,
//            focusedElevation = 0.dp,
//            hoveredElevation = 0.dp,
//            draggedElevation = 0.dp,
//            disabledElevation = 0.dp,
//        ),
//        modifier = modifier
//    ) {
//        Column(
//            modifier = Modifier
//                .background(brush = brush, alpha = .4f)
//                .fillMaxWidth()
//                .padding(8.dp),
//        ) {
//
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//
//                EditableText(
//                    label = "Name",
//                    readOnly = !editState || !isAllowedToChangeItem,
//                    value = customerEnquiryState.item.name,
//                    onValueChange = {
//                        customerEnquiryState.item.name = it ?: ""
//                        onChange(customerEnquiryState)
//                    },
//                    singleLine = true,
//                    modifier = Modifier.weight(1F)
//                )
//                if (editState)
//                    IconButton(
//                        onClick = onDelete,
//                        modifier = Modifier.padding(end = 6.dp)
//                    ) {
//                        Icon(imageVector = Icons.Default.Close, contentDescription = "Delete")
//                    }
//            }
//
//            Row(
//                horizontalArrangement = Arrangement.Start,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                EditableText(
//                    label = "Type",
//                    readOnly = !editState || !isAllowedToChangeItem,
//                    value = customerEnquiryState.item.type ?: "",
//                    onValueChange = {
//                        customerEnquiryState.item.type = it
//                        onChange(customerEnquiryState)
//                    },
//                    singleLine = true,
//                    modifier = Modifier.weight(1F)
//                )
//                EditableText(
//                    label = "Model No",
//                    readOnly = !editState || !isAllowedToChangeItem,
//                    value = customerEnquiryState.item.modelNo ?: "",
//                    onValueChange = {
//                        customerEnquiryState.item.modelNo = it
//                        onChange(customerEnquiryState)
//                    },
//                    singleLine = true,
//                    modifier = Modifier.weight(1F)
//                )
//            }
//
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(IntrinsicSize.Max)
//            ) {
//
//                EditableText(
//                    label = "Description",
//                    readOnly = !editState,
//                    value = customerEnquiryState.note,
//                    onValueChange = {
//                        customerEnquiryState.note = it ?: ""
//                        onChange(customerEnquiryState)
//                    },
//                    singleLine = false,
//                    modifier = Modifier
//                        .padding(top = 8.dp)
//                        .weight(1F)
//                        .fillMaxHeight()
//                )
//
//                QuantityChanger(
//                    quantity = customerEnquiryState.quantity,
//                    unit = customerEnquiryState.item.unit ?: "Unit",
//                    editState = editState,
//                ) {
//                    customerEnquiryState.quantity = it
//                    onChange(customerEnquiryState)
//                }
//            }
//        }
//    }
//}


package app.duss.easyproject.presentation.ui.ce.details.components

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
import app.duss.easyproject.domain.entity.CustomerEnquiryItem
import app.duss.easyproject.presentation.components.EditableText

@Composable
fun CEItemContent(
    modifier: Modifier = Modifier,
    customerEnquiryItem: CustomerEnquiryItem,
    onChange: (CustomerEnquiryItem) -> Unit,
    editState: Boolean = false,
    isAllowedToChangeItem: Boolean = false,
    brush: Brush,
    onDelete: () -> Unit,
) {
    val customerEnquiryState by remember { mutableStateOf(customerEnquiryItem) }
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
                EditableText(
                    label = "Name",
                    readOnly = !editState || !isAllowedToChangeItem,
                    value = customerEnquiryState.item.name,
                    onValueChange = {
                        customerEnquiryState.item.name = it ?: ""
                        onChange(customerEnquiryState)
                    },
                    singleLine = true,
                )

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    EditableText(
                        label = "Type",
                        readOnly = !editState || !isAllowedToChangeItem,
                        value = customerEnquiryState.item.type ?: "",
                        onValueChange = {
                            customerEnquiryState.item.type = it
                            onChange(customerEnquiryState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                    EditableText(
                        label = "Model No",
                        readOnly = !editState || !isAllowedToChangeItem,
                        value = customerEnquiryState.item.modelNo ?: "",
                        onValueChange = {
                            customerEnquiryState.item.modelNo = it
                            onChange(customerEnquiryState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                }

//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(IntrinsicSize.Max)
//            ) {

                EditableText(
                    label = "Description",
                    readOnly = !editState,
                    value = customerEnquiryState.note,
                    onValueChange = {
                        customerEnquiryState.note = it ?: ""
                        onChange(customerEnquiryState)
                    },
                    singleLine = false,
                    minLines = 4,
                    maxLines = 4,
                    modifier = Modifier
                        .padding(top = 8.dp)
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
                    if (editState) Icon(imageVector = Icons.Default.Close, contentDescription = "Delete")
                    else Spacer(Modifier.size(24.dp))
                }

                QuantityChanger(
                    quantity = customerEnquiryState.quantity,
                    unit = customerEnquiryState.item.unit ?: "Unit",
                    editState = editState,
                ) {
                    customerEnquiryState.quantity = it
                    onChange(customerEnquiryState)
                }

                Spacer(Modifier.height(48.dp))

            }
        }
    }
}