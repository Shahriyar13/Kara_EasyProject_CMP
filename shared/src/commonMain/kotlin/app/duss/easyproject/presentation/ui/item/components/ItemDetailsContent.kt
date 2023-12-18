package app.duss.easyproject.presentation.ui.item.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.presentation.components.EditableText
import app.duss.easyproject.presentation.components.LabelledModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsContent(
    init: Item,
    onDismiss: () -> Unit,
    onSave: (Item) -> Unit,
) {
    val detailsState by remember { mutableStateOf(init) }
    var editState by remember { mutableStateOf((detailsState.id ?: -1) <= 0) }
    val modalBottomSheetState = rememberModalBottomSheetState()

    LabelledModalBottomSheet(
        title = if ((detailsState.id ?: -1) > 0) "Item: ${detailsState.name}" else "New Item",
        editState = editState,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        onSave = {
            onSave(detailsState)
        },
        onEdit = {
            editState = true
        },
        onDismiss = onDismiss,
    ) {

        EditableText(
            readOnly = !editState,
            label = "Name",
            value = detailsState.name,
            onValueChange = {
                detailsState.name = it ?: ""
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EditableText(
                readOnly = !editState,
                label = "Type",
                value = detailsState.type,
                onValueChange = {
                    detailsState.type = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = !editState,
                label = "Model No.",
                value = detailsState.modelNo,
                onValueChange = {
                    detailsState.modelNo = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EditableText(
                readOnly = !editState,
                label = "Unit",
                value = detailsState.unit,
                onValueChange = {
                    detailsState.unit = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = !editState,
                label = "HS Code",
                value = detailsState.hsCodeEu,
                onValueChange = {
                    detailsState.hsCodeEu = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EditableText(
                readOnly = true,
                label = "Parent Item",
                value = detailsState.parentItem?.name,
//                onValueChange = {
//                    detailsState.parentItem = it ?: ""
//                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = true,
                label = "Sub Items",
                value = detailsState.subItems?.map { it.name }?.toString() ?: "",
//                onValueChange = {
//                    detailsState.taxNumber = it ?: ""
//                },
                modifier = Modifier.weight(1F),
            )
        }

        EditableText(
            readOnly = !editState,
            label = "Description",
            value = detailsState.note,
            onValueChange = {
                detailsState.note = it ?: ""
            },
            modifier = Modifier.fillMaxWidth(),
        )

    }
}