package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.duss.easyproject.domain.entity.QuotationItem

@Composable
fun QuotationItemContent(
    modifier: Modifier = Modifier,
    item: QuotationItem,
    brush: Brush,
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
                .padding(8.dp),
        ) {
            Text(
                text = "Name: ${itemState.customerEnquiryItem.item.name}",
                fontSize = 18.sp
            )

            Text(
                text = "Remaining: ${item.quantity} ${item.customerEnquiryItem.item.unit ?: "Unit"}",
                fontSize = 14.sp
            )
        }
    }
}