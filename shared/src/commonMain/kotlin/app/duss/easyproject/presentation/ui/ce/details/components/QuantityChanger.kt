package app.duss.easyproject.presentation.ui.ce.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuantityChanger(
    quantity: Int,
    unit: String,
    editState: Boolean,
    onChange: (Int) -> Unit,
) {
    var quantityState by remember { mutableIntStateOf(quantity) }

    Column(
        modifier = Modifier
            .width(60.dp)
            .padding(0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            enabled = editState,
            onClick = {
                if (editState) onChange(++quantityState)
            }) {
            if (editState) Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Add")
            else Spacer(Modifier.size(24.dp))
        }
        BasicTextField(
            value = "$quantityState",
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            ),
            readOnly = !editState,
            enabled = editState,
            singleLine = true,
            onValueChange = {
                val i = it.toIntOrNull() ?: 0
                onChange(if (i > 0) i else 1)
            },
        )
        BasicText(
            text = unit,
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        IconButton(
            enabled = editState && quantityState > 1,
            onClick = {
                if (editState) onChange(--quantityState)
            }) {
            if (editState) Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Add")
            else Spacer(Modifier.size(24.dp))
        }
    }
}