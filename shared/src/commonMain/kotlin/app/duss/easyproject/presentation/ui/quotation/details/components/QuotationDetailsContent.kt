package app.duss.easyproject.presentation.ui.quotation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.components.EditableText
import app.duss.easyproject.presentation.components.TopAppBarDocumentDetails
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.quotation.details.QuotationDetailsComponent
import app.duss.easyproject.presentation.ui.quotation.details.store.QuotationDetailsStore
import kotlinx.coroutines.launch

@Composable
internal fun QuotationDetailsContent(
    state: QuotationDetailsStore.State,
    onEvent: (QuotationDetailsStore.Intent) -> Unit,
    onOutput: (QuotationDetailsComponent.Output) -> Unit,
    modifier: Modifier = Modifier.padding(LocalSafeArea.current),
    component: QuotationDetailsComponent,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(contentAlignment = Alignment.TopCenter) {

        if (state.isDeleted) {
            onOutput(QuotationDetailsComponent.Output.NavigateBack(state.item!!.id))
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBarDocumentDetails(
                    onBack = {
                        onOutput(
                            QuotationDetailsComponent.Output.NavigateBack(
                                updatedItem = if (state.isUpdated) state.item else null,
                            )
                        )
                    },
                    onSave = {
                        if (!state.isLoading && state.item != null)
                            onEvent(QuotationDetailsStore.Intent.SaveState(state.item, state.isChanged))
                    },
                    onEdit = {
                        onEvent(QuotationDetailsStore.Intent.EditState)
                    },
                    editState = state.inEditeMode,
                    showUnsavedChanges = state.isChanged,
                    title = if ((state.item?.id ?: -1) > 0) "Customer Enquiry: " + (state.item?.code ?: "") else "New Customer Enquiry",
                    loadingState = state.isLoading
                )
            },
            containerColor = Color.Transparent,
            modifier = modifier
        ) { paddingValue ->
            Box(
                modifier = Modifier
                    .padding(paddingValue)
                    .padding(20.dp)
            ) {
                if (state.isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.error?.let { error ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            error,
                            withDismissAction = true,
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }

                state.item?.let { item ->
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item("name") {
                            EditableText(
                                value = item.code?.replaceFirstChar { it.uppercaseChar() },
                                onValueChange = {
                                    onEvent(
                                        QuotationDetailsStore.Intent.EditingState(
                                            item.copy(
                                                code = it ?: ""
                                            )
                                        )
                                    )
                                },
                                label = "Code",
                                readOnly = !state.inEditeMode,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

//                        item("title") {
//                            EditableText(
//                                value = item.title,
//                                onValueChange = {
//                                    onEvent(
//                                        QuotationDetailsStore.Intent.EditingState(
//                                            item.copy(
//                                                title = it ?: ""
//                                            )
//                                        )
//                                    )
//
//                                },
//                                label = "Title",
//                                readOnly = !state.inEditeMode,
//                                modifier = Modifier.fillMaxWidth()
//                            )
//                        }

                        item("items") {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    "List of Customer-Enquiry-Items:",
                                    modifier
                                        .padding(top = 26.dp, bottom = 16.dp)
                                )
                                if (state.inEditeMode) {
                                    Button(
                                        onClick = {
                                            component.itemPickerSelected()
                                        }
                                    ) {
                                        Icon(Icons.Default.List, "List")
                                        Text("Open Item List")
                                    }
                                }
                            }

                            QuotationItemColumn(
                                item.quotationItems,
                                state.inEditeMode,
                                onItemUpdate = { changedItem ->
                                    item.quotationItems = item.quotationItems.map {
                                        if ((it.customerEnquiryItem.id != null && it.customerEnquiryItem.id == changedItem.customerEnquiryItem.id) || it.hashCode() == changedItem.hashCode()) { //TODO check for item.id null
                                            it.copy(
                                                quantity = changedItem.quantity,
                                                note = changedItem.note,
                                                customerEnquiryItem = changedItem.customerEnquiryItem,
                                            )
                                        } else {
                                            it
                                        }
                                    }

                                    onEvent(
                                        QuotationDetailsStore.Intent.EditingState(
                                            item
                                        )
                                    )
                                },
                                onItemDelete = { deletedItem ->
                                    item.quotationItems = item.quotationItems.minus(deletedItem)

                                    onEvent(
                                        QuotationDetailsStore.Intent.EditingState(
                                            item
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}