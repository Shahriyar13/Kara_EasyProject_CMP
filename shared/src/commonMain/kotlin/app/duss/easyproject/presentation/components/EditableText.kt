package app.duss.easyproject.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
internal fun EditableText(
    value: String?,
    label: String = "",
    onValueChange: (String?) -> Unit = {},
    readOnly: Boolean = true,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else 4,
    minLines: Int = 1,
    modifier: Modifier = Modifier
        .widthIn(max = 440.dp)
        .padding(horizontal = 20.dp, vertical = 20.dp),
) {
    var textValue by remember { mutableStateOf(value) }
    val containerColor = MaterialTheme.colorScheme.surface.copy(alpha = if (readOnly) 0f else .1f)

    TextField(
        value = textValue ?: "",
        onValueChange = {
            textValue = it
            onValueChange(it)
        },
        supportingText = {
            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (!errorMessage.isNullOrEmpty()) {
                Icon(
                    Icons.Filled.Error,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = errorMessage,
                )
            }
        },
        label = { Text(label) },
        readOnly = readOnly,
        maxLines = maxLines,
        minLines = minLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        singleLine = singleLine,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLeadingIconColor = MaterialTheme.colorScheme.surface,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.surface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
        ),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier,
    )
}