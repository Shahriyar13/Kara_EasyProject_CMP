package app.duss.easyproject.presentation.components

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditableText(
    modifier: Modifier = Modifier,
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
    interactionSource: InteractionSource = MutableInteractionSource(),
) {
    var textValue by remember { mutableStateOf(value) }
    val containerColor = MaterialTheme.colorScheme.surface.copy(alpha = if (readOnly) 0f else .1f)

    BasicTextField(
        value = textValue ?: "",
        onValueChange = {
            textValue = it
            onValueChange(it)
        },
        readOnly = readOnly,
        maxLines = maxLines,
        minLines = minLines,
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        singleLine = singleLine,
        modifier = modifier,
    ) {
        TextFieldDefaults.DecorationBox(
            value = textValue ?: "",
            innerTextField = it,
            singleLine = singleLine,
            enabled = true,
            visualTransformation = VisualTransformation.None,
            label = { Text("$label:") },
//            label = { if (!readOnly || !textValue.isNullOrEmpty()) Text("$label:") },//TODO make it better
            trailingIcon = {
                if (!errorMessage.isNullOrEmpty()) {
                    Icon(
                        Icons.Filled.Warning,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = errorMessage,
                    )
                }
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
            interactionSource = interactionSource,
            contentPadding = TextFieldDefaults.contentPaddingWithLabel(
                start = 8.dp,
                top = 8.dp
            ),
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
        )
    }
}