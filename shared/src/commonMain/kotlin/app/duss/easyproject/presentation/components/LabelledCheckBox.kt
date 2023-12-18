package app.duss.easyproject.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun LabelledCheckBox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    label: String,
    checkedValue: String = "Is $label",
    uncheckedValue: String = "Not $label",
    readOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    var checkState by remember { mutableStateOf(checked) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                indication =
                    if (!readOnly) rememberRipple(color = MaterialTheme.colorScheme.primary)
                    else null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    if (!readOnly) {
                        checkState = !checkState
                        onCheckedChange(checkState)
                    }
                }
            )
            .requiredHeight(ButtonDefaults.MinHeight)
            .padding(4.dp)
    ) {

        if (readOnly) {
            Text(text = if (checkState) checkedValue else uncheckedValue)
        } else {
            Checkbox(checked = checkState, onCheckedChange = null)

            Spacer(Modifier.size(6.dp))

            Text(text = label)
        }
    }
}