package app.duss.easyproject.presentation.ui.person.components

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.presentation.components.EditableText
import app.duss.easyproject.presentation.components.LabelledModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsContent(
    init: Person,
    onDismiss: () -> Unit,
    onSave: (Person) -> Unit,
) {
    val detailsState by remember { mutableStateOf(init) }
    var editState by remember { mutableStateOf((detailsState.id ?: -1) <= 0) }
    val modalBottomSheetState = rememberModalBottomSheetState()

    LabelledModalBottomSheet(
        title = if ((detailsState.id ?: -1) > 0) "Person: ${detailsState.fullName()}" else "New Person",
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EditableText(
                readOnly = !editState,
                label = "Firstname",
                value = detailsState.firstName,
                onValueChange = {
                    detailsState.firstName = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = !editState,
                label = "Lastname",
                value = detailsState.lastName,
                onValueChange = {
                    detailsState.lastName = it ?: ""
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
                label = "Job title",
                value = detailsState.jobTitle,
                onValueChange = {
                    detailsState.jobTitle = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = !editState,
                label = "Email",
                value = detailsState.email,
                keyboardType = KeyboardType.Email,
                onValueChange = {
                    detailsState.email = it ?: ""
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
                label = "Telephone",
                keyboardType = KeyboardType.Phone,
                value = detailsState.telephone,
                onValueChange = {
                    detailsState.telephone = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = !editState,
                label = "Mobile",
                keyboardType = KeyboardType.Phone,
                value = detailsState.mobile,
                onValueChange = {
                    detailsState.mobile = it ?: ""
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
                label = "Company",
                value = detailsState.company?.name,
//                onValueChange = {
//                    detailsState.parentItem = it ?: ""
//                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = true,
                label = "Individual Company",
                value = detailsState.individualCompanyName ?: "",
                onValueChange = {
                    detailsState.individualCompanyName = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
        }

    }
}