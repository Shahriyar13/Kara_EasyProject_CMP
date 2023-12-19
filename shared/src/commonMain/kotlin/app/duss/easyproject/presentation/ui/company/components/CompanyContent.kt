package app.duss.easyproject.presentation.ui.company.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.components.LabelledRadio
import app.duss.easyproject.presentation.components.SimpleListItemContent
import app.duss.easyproject.presentation.components.SimplePagingVerticalGrid
import app.duss.easyproject.presentation.components.TopAppBarDocumentList
import app.duss.easyproject.presentation.ui.company.CompanyComponent
import app.duss.easyproject.presentation.ui.company.CompanyFilter
import app.duss.easyproject.presentation.ui.company.store.CompanyStore
import kotlinx.coroutines.launch

@Composable
internal fun CompanyContent(
    state: CompanyStore.State,
    onEvent: (CompanyStore.Intent) -> Unit,
    onOutput: (CompanyComponent.Output) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBarDocumentList(
                title = when (state.filter) {
                    CompanyFilter.Supplier -> "Suppliers"
                    CompanyFilter.Customer -> "Customers"
                    CompanyFilter.FreightForwarder -> "FreightForwarders"
                    else -> "Companies"
                },
                onSearchValueChange = {
                    onEvent(CompanyStore.Intent.UpdateSearchValue(it))
                },
                searchValue = state.searchValue,
                loadingState = state.isLoading,
                onAddClicked = {
                    onEvent(CompanyStore.Intent.New)
                },
            ) {
                if (!state.selectMode) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                    ) {
                        LabelledRadio(
                            checked = state.filter == CompanyFilter.ALL,
                            onCheckedChange = {
                                onEvent(
                                    CompanyStore.Intent.Filter(CompanyFilter.ALL)
                                )
                            },
                            label = "All Companies",
                        )
                        LabelledRadio(
                            checked = state.filter == CompanyFilter.Customer,
                            onCheckedChange = {
                                onEvent(
                                    CompanyStore.Intent.Filter(CompanyFilter.Customer)
                                )
                            },
                            label = "Customers",
                        )
                        LabelledRadio(
                            checked = state.filter == CompanyFilter.Supplier,
                            onCheckedChange = {
                                onEvent(
                                    CompanyStore.Intent.Filter(CompanyFilter.Supplier)
                                )
                            },
                            label = "Suppliers",
                        )
                        LabelledRadio(
                            checked = state.filter == CompanyFilter.FreightForwarder,
                            onCheckedChange = {
                                onEvent(
                                    CompanyStore.Intent.Filter(CompanyFilter.FreightForwarder)
                                )
                            },
                            label = "Freight Forwarders",
                        )
                    }
                }
            }
        },
    ) { paddingValue ->

        state.error?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    error,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite
                )
            }
        }

        state.detail?.let { detail ->
            CompanyDetailsContent(
                init = detail,
                people = state.people,
                cities = state.cities,
                onDismiss = {
                    onEvent(CompanyStore.Intent.EditDone)
                }
            ) {
                onEvent(CompanyStore.Intent.Update(it))
            }
        }

        SimplePagingVerticalGrid(
            itemList = state.list,
            loadMoreItems = {
                if (state.list.isEmpty()) return@SimplePagingVerticalGrid
                val nextPage = state.page + 1
                onEvent(CompanyStore.Intent.LoadByPage(page = nextPage))
            },
            isLoading = state.isLoading,
            isLastPageLoaded = state.isLastPageLoaded,
            modifier = Modifier.padding(paddingValue)
        ) { item, brush ->
            SimpleListItemContent(
                onClick = {
                    if (state.selectMode) {
                        onEvent(CompanyStore.Intent.UpdateSelected(item = item))
                    } else {
                        item.id?.let { id ->
                            onEvent(CompanyStore.Intent.Edit(id = id))
                        }
                    }
                },
                title = item.name,
                subtitle = item.getAddress().let { "Address: $it" },
                caption = "${item.symbol?.let { "Symbol: $it  " }}${item.customerCode?.let { "Customer: $it  " }}${item.supplierCode?.let { "Supplier: $it  " }}${item.freightForwarderCode?.let { "FreightForwarder: $it" }}",
                brush = brush
            )
        }
    }
}