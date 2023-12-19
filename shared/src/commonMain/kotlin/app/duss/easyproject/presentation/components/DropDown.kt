package app.duss.easyproject.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownMenu(
    options: List<T> = listOf(),
    value: T?,
    label: String = "",
    onValueChange: (T?) -> Unit,
    onNoneSelect: (() -> Unit)? = null,
    onAddNewSelect: (() -> Unit)? = null,
    readOnly: Boolean = true,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
        .widthIn(max = 440.dp)
        .padding(horizontal = 20.dp, vertical = 20.dp),
) {

    var isExpanded by remember { mutableStateOf(false) }

    var selectedOption by remember { mutableStateOf(value) }

    var visibleOptions by remember { mutableStateOf(options) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier,
//        expanded = isExpanded,
//        onExpandedChange = {
//            isExpanded = it
//        }
    ) {
        BasicEditableText(
            label = label,
            readOnly = readOnly,
            errorMessage = errorMessage,
            keyboardType = keyboardType,
            imeAction = imeAction,
            value = selectedOption?.toString() ?: "",
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            onValueChange = { text ->
                if ((text?.length ?: 0) > 3) {
                    scope.launch {
                        visibleOptions = if (text.isNullOrEmpty()) {
                            options
                        } else {
                            options.filter {
                                it.toString().lowercase().startsWith(text.lowercase())
                            }
                        }
                        isExpanded = true
                    }
                }
            },
//            modifier = Modifier.menuAnchor().focusRequester(focusRequester),
        )

        DropdownMenu(
            properties = PopupProperties(focusable = false),
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
        ) {
            if (onAddNewSelect != null)
                DropdownMenuItem(
                    text = { Text("Add New") },
                    onClick = {
                        isExpanded = false
                        selectedOption = null
                        onAddNewSelect()
                    }
                )
            if (onNoneSelect != null)
                DropdownMenuItem(
                    text = { Text("None") },
                    onClick = {
                        isExpanded = false
                        selectedOption = null
                        onNoneSelect()
                    }
                )
            visibleOptions.map { option ->
                DropdownMenuItem(
                    text = { Text(option.toString()) },
                    onClick = {
                        isExpanded = false
                        selectedOption = option
                        onValueChange(selectedOption)
                    }
                )
            }
        }
    }
}