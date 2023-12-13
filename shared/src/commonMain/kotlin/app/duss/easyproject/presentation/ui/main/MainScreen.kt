package app.duss.easyproject.presentation.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.GifBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.ui.comingsoon.ComingSoonScreen
import app.duss.easyproject.presentation.ui.dashboard.DashboardScreen
import app.duss.easyproject.presentation.ui.database.DatabaseScreen
import app.duss.easyproject.presentation.ui.project.list.ProjectListScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
internal fun MainScreen(component: MainComponent) {

    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    class MenuItemData(
        val title: String,
        val icon: ImageVector,
        val onClick: () -> Unit,
        val isSelected: Boolean,
    )

    val menuItems = listOf(
        MenuItemData(
            title = "Dashboard",
            icon = Icons.Default.Dashboard,
            onClick = component::onDashboardTabClicked,
            isSelected = activeComponent is MainComponent.Child.DashboardChild,
        ),
        MenuItemData(
            title = "Database",
            icon = Icons.Default.DataArray,
            onClick = component::onDatabaseTabClicked,
            isSelected = activeComponent is MainComponent.Child.DatabaseChild,
        ),
        MenuItemData(
            title = "Customer Enquiry",
            icon = Icons.Default.QuestionMark,
            onClick = component::onCETabClicked,
            isSelected = activeComponent is MainComponent.Child.CEChild,
        ),
        MenuItemData(
            title = "Project",
            icon = Icons.Default.Backpack,
            onClick = component::onProjectTabClicked,
            isSelected = activeComponent is MainComponent.Child.ProjectChild,
        ),
        MenuItemData(
            title = "Supplier Enquiry",
            icon = Icons.Default.QuestionMark,
            onClick = component::onSETabClicked,
            isSelected = activeComponent is MainComponent.Child.SEChild,
        ),
        MenuItemData(
            title = "Supplier Quotation",
            icon = Icons.Default.FormatQuote,
            onClick = component::onSQTabClicked,
            isSelected = activeComponent is MainComponent.Child.SQChild,
        ),
        MenuItemData(
            title = "Proforma Invoice",
            icon = Icons.Default.List,
            onClick = component::onPITabClicked,
            isSelected = activeComponent is MainComponent.Child.PIChild,
        ),
        MenuItemData(
            title = "Purchase Order",
            icon = Icons.Default.List,
            onClick = component::onPOTabClicked,
            isSelected = activeComponent is MainComponent.Child.POChild,
        ),
        MenuItemData(
            title = "Packing",
            icon = Icons.Default.GifBox,
            onClick = component::onPackingTabClicked,
            isSelected = activeComponent is MainComponent.Child.PackingChild,
        ),
        MenuItemData(
            title = "Invoice",
            icon = Icons.Default.RequestQuote,
            onClick = component::onInvoiceTabClicked,
            isSelected = activeComponent is MainComponent.Child.InvoiceChild,
        ),
        MenuItemData(
            title = "BAFA",
            icon = Icons.Default.FactCheck,
            onClick = component::onBafaTabClicked,
            isSelected = activeComponent is MainComponent.Child.BAFAChild,
        ),
        MenuItemData(
            title = "Payment",
            icon = Icons.Default.Payments,
            onClick = component::onPaymentTabClicked,
            isSelected = activeComponent is MainComponent.Child.PaymentChild,
        ),
        MenuItemData(
            title = "Profile",
            icon = Icons.Default.Person,
            onClick = component::onProfileTabClicked,
            isSelected = activeComponent is MainComponent.Child.ProfileChild,
        ),
    )

    val containerColor = MaterialTheme.colorScheme.background
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onBackground

    PermanentNavigationDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.width(200.dp).verticalScroll(state = rememberScrollState()),
                drawerContainerColor = containerColor,
            ) {
                menuItems.map {
                    NavigationDrawerItem(
                        label = {
                            Text(
                                it.title,
                                softWrap = false,
                                color = if (it.isSelected) selectedColor else unselectedColor
                            )
                        },
                        selected = it.isSelected,
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.title,
                                tint = if (it.isSelected) selectedColor else unselectedColor
                            )
                        },
                        onClick = it.onClick,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = containerColor,
                            unselectedContainerColor = containerColor,
                            selectedIconColor = selectedColor,
                            unselectedIconColor = unselectedColor,
                            selectedTextColor = selectedColor,
                            unselectedTextColor = unselectedColor,
                            selectedBadgeColor = selectedColor,
                            unselectedBadgeColor = unselectedColor,
                        )
                    )
                }
            }

        },
        content = {
            Children(
                stack = component.childStack,
                animation = stackAnimation(fade()),
            ) {
                when (val child = it.instance) {
                    is MainComponent.Child.DashboardChild -> DashboardScreen(child.component)
                    is MainComponent.Child.DatabaseChild -> DatabaseScreen(child.component)
                    is MainComponent.Child.CEChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.ProjectChild -> ProjectListScreen(child.component)
                    is MainComponent.Child.SEChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.ComingSoonChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.BAFAChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.InvoiceChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.PIChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.POChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.PaymentChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.ProfileChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.SQChild -> ComingSoonScreen(child.component)
                    is MainComponent.Child.PackingChild -> ComingSoonScreen(child.component)
                }
            }
        }
    )
}