package app.duss.easyproject.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DataArray
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RequestQuote
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ViewList
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.duss.easyproject.ui.helper.LocalSafeArea
import app.duss.easyproject.ui.main.components.MainContent
import app.duss.easyproject.ui.main.components.MainModalDrawerSheet
import app.duss.easyproject.ui.main.store.MainStore
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(component: MainComponent) {

    val state by component.state.collectAsState()

    val items = listOf(
        "Home" to Icons.Outlined.Home,
        "Database" to Icons.Outlined.DataArray,
        "Customer Enquiry" to Icons.Outlined.RequestQuote,
        "Projects" to Icons.Outlined.ViewList,
        "Supplier Enquiry" to Icons.Outlined.RequestQuote,
        "Supplier Quotation" to Icons.Outlined.RequestQuote,
        "Proforma Invoice" to Icons.Outlined.RequestQuote,
        "Purchase Order" to Icons.Outlined.RequestQuote,
        "Shipping List" to Icons.Outlined.RequestQuote,
        "Invoice" to Icons.Outlined.RequestQuote,
        "BAFA" to Icons.Outlined.RequestQuote,
        "Payment" to Icons.Outlined.RequestQuote,
        "Profile & Settings" to Icons.Outlined.Settings,
    )

    var selectedItem by remember { mutableStateOf(items[0]) }

    LaunchedEffect(selectedItem) {
        when (selectedItem.first) {
            "Database" -> {
                component.onOutput(MainComponent.Output.DatabaseClicked)
            }
            "Customer Enquiry" -> {
                component.onOutput(MainComponent.Output.CEClicked)
            }
            "Projects" -> {
                component.onOutput(MainComponent.Output.ProjectClicked)
            }
            "Supplier Enquiry" -> {
                component.onOutput(MainComponent.Output.SEClicked)
            }
            "Supplier Quotation" -> {
                component.onOutput(MainComponent.Output.SQClicked)
            }
            "Proforma Invoice" -> {
                component.onOutput(MainComponent.Output.PIClicked)
            }
            "Purchase Order" -> {
                component.onOutput(MainComponent.Output.POClicked)
            }
            "Shipping List" -> {
                component.onOutput(MainComponent.Output.ShippingClicked)
            }
            "Invoice" -> {
                component.onOutput(MainComponent.Output.InvoiceClicked)
            }
            "BAFA" -> {
                component.onOutput(MainComponent.Output.BafaClicked)
            }
            "Payment" -> {
                component.onOutput(MainComponent.Output.PaymentClicked)
            }
            "Profile & Settings" -> {
                component.onOutput(MainComponent.Output.ProfileClicked)
            }
        }
    }

    BoxWithConstraints {
        if (maxWidth > 1199.dp) {
            MainContentLarge(
                state = state,
                onEvent = component::onEvent,
                onOutput = component::onOutput,
                items = items,
                selectedItem = selectedItem,
                updateSelectedItem = { selectedItem = it }
            )
        } else {
            MainContentDefault(
                state = state,
                onEvent = component::onEvent,
                onOutput = component::onOutput,
                items = items,
                selectedItem = selectedItem,
                updateSelectedItem = { selectedItem = it }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainContentDefault(
    state: MainStore.State,
    onEvent: (MainStore.Intent) -> Unit,
    onOutput: (MainComponent.Output) -> Unit,
    items: List<Pair<String, ImageVector>>,
    selectedItem: Pair<String, ImageVector>,
    updateSelectedItem: (Pair<String, ImageVector>) -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainModalDrawerSheet(
                items = items,
                selectedItem = selectedItem,
                onItemsClick = { item ->
                    scope.launch { drawerState.close() }
                    updateSelectedItem(item)
                }
            )
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch { drawerState.open() }
                                },
                            ) {
                                Icon(Icons.Rounded.Menu, contentDescription = null)
                            }
                        },
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                },
                modifier = Modifier.padding(LocalSafeArea.current)
            ) { paddingValues ->
                MainContent(
                    state = state,
                    onEvent = onEvent,
                    onOutput = onOutput,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainContentLarge(
    state: MainStore.State,
    onEvent: (MainStore.Intent) -> Unit,
    onOutput: (MainComponent.Output) -> Unit,
    items: List<Pair<String, ImageVector>>,
    selectedItem: Pair<String, ImageVector>,
    updateSelectedItem: (Pair<String, ImageVector>) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        MainModalDrawerSheet(
            items = items,
            selectedItem = selectedItem,
            onItemsClick = { item ->
                updateSelectedItem(item)
            }
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            modifier = Modifier.padding(LocalSafeArea.current)
        ) { paddingValues ->
            MainContent(
                state = state,
                onEvent = onEvent,
                onOutput = onOutput,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}