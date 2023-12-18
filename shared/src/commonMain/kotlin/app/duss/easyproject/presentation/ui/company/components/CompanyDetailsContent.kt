package app.duss.easyproject.presentation.ui.company.components

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
import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.presentation.components.EditableText
import app.duss.easyproject.presentation.components.LabelledCheckBox
import app.duss.easyproject.presentation.components.LabelledModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsContent(
    init: Company,
    onDismiss: () -> Unit,
    onSave: (Company) -> Unit,
) {
    val detailsState by remember { mutableStateOf(init) }
    var editState by remember { mutableStateOf((detailsState.id ?: -1 ) <= 0 ) }
    val modalBottomSheetState = rememberModalBottomSheetState()

    LabelledModalBottomSheet(
        title = if ((detailsState.id ?: -1) > 0 ) "Company: ${detailsState.name}" else "New Company",
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
                label = "Name",
                value = detailsState.name,
                onValueChange = {
                    detailsState.name = it ?: ""
                },
                modifier = Modifier.weight(3F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = !editState,
                label = "Symbol",
                value = detailsState.symbol,
                onValueChange = {
                    detailsState.symbol = it ?: ""
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
                label = "Email",
                keyboardType = KeyboardType.Email,
                value = detailsState.email,
                onValueChange = {
                    detailsState.email = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = !editState,
                label = "Website",
                keyboardType = KeyboardType.Uri,
                value = detailsState.website,
                onValueChange = {
                    detailsState.website = it ?: ""
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
                label = "Fax",
                keyboardType = KeyboardType.Phone,
                value = detailsState.fax,
                onValueChange = {
                    detailsState.fax = it ?: ""
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
                label = "VAT No.",
                value = detailsState.vatNumber,
                onValueChange = {
                    detailsState.vatNumber = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = !editState,
                label = "TAX No.",
                value = detailsState.taxNumber,
                onValueChange = {
                    detailsState.taxNumber = it ?: ""
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
                label = "Address",
                value = detailsState.street,
                onValueChange = {
                    detailsState.street = it ?: ""
                },
                modifier = Modifier.weight(2F),
            )
            Spacer(Modifier.size(16.dp))
            EditableText(
                readOnly = !editState,
                label = "Postcode",
                value = detailsState.postcode,
                onValueChange = {
                    detailsState.postcode = it ?: ""
                },
                modifier = Modifier.weight(1F),
            )
            EditableText(
                readOnly = !editState,
                label = "City",
                value = detailsState.city?.name ?: "",
                onValueChange = {
                    //TODO
                },
                modifier = Modifier.weight(1F),
            )
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            LabelledCheckBox(
                label = "Customer",
                readOnly = !editState,
                checkedValue = "Customer Code: ${detailsState.customerCode}",
                uncheckedValue = "Not a Customer${if (!detailsState.customerCode.isNullOrEmpty()) " Assigned Code: ${detailsState.customerCode}" else ""}",
                checked = detailsState.isCustomer,
                onCheckedChange = {
                    detailsState.isCustomer = it
                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            LabelledCheckBox(
                label = "Supplier",
                readOnly = !editState,
                checkedValue = "Supplier Code: ${detailsState.supplierCode}",
                uncheckedValue = "Not a Supplier${if (!detailsState.supplierCode.isNullOrEmpty()) " Assigned Code: ${detailsState.supplierCode}" else ""}",
                checked = detailsState.supplierCode != null,
                onCheckedChange = {
                    detailsState.isSupplier = it
                },
                modifier = Modifier.weight(1F),
            )
            Spacer(Modifier.size(16.dp))
            LabelledCheckBox(
                label = "Freight Forwarder",
                readOnly = !editState,
                checkedValue = "Freight Forwarder Code: ${detailsState.freightForwarderCode}",
                uncheckedValue = "Not a Freight Forwarder${if (!detailsState.freightForwarderCode.isNullOrEmpty()) " Assigned Code: ${detailsState.freightForwarderCode}" else ""}",
                checked = detailsState.freightForwarderCode != null,
                onCheckedChange = {
                    detailsState.isFreightForwarder = it
                },
                modifier = Modifier.weight(1F),
            )
        }
    }
}