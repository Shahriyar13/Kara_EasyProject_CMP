package app.duss.easyproject.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelledModalBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    editState: Boolean,
    sheetState: SheetState,
    onSave: () -> Unit,
    onEdit: () -> Unit,
    onDismiss: () -> Unit,
    dragHandle: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        dragHandle = dragHandle,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier)
            Text(title)
            if (editState) {
                IconButton(
                    onClick = {
                        onSave()
                    }
                ) {
                    Icon(Icons.Filled.Save, "save")
                }
            }
            else {
                IconButton(
                    onClick = {
                        onEdit()
                    }
                ) {
                    Icon(Icons.Filled.Edit, "Edit")
                }
            }
        }
        Divider()

        Column(modifier = modifier
            .padding(start = 12.dp, end = 12.dp, top = 12.dp)
            .verticalScroll(rememberScrollState()),
        ) {
            content()
            Spacer(Modifier.size(48.dp))
        }
    }
}