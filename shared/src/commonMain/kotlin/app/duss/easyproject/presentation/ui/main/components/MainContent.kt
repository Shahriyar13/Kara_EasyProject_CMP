package app.duss.easyproject.presentation.ui.main.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DataArray
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
import app.duss.easyproject.presentation.ui.main.MainComponent
import app.duss.easyproject.presentation.ui.project.list.ProjectListScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
fun MainContent(component: MainComponent) {

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
            isSelected = activeComponent is MainComponent.Child.Dashboard,
        ),
        MenuItemData(
            title = "Database",
            icon = Icons.Default.DataArray,
            onClick = component::onDatabaseTabClicked,
            isSelected = activeComponent is MainComponent.Child.Database,
        ),
        MenuItemData(
            title = "Customer Enquiry",
            icon = Icons.Default.RequestQuote,
            onClick = component::onCETabClicked,
            isSelected = activeComponent is MainComponent.Child.CE,
        ),
        MenuItemData(
            title = "Project",
            icon = Icons.Default.Dashboard,
            onClick = component::onProjectTabClicked,
            isSelected = activeComponent is MainComponent.Child.Project,
        ),
        MenuItemData(
            title = "Supplier Enquiry",
            icon = Icons.Default.RequestQuote,
            onClick = component::onSETabClicked,
            isSelected = activeComponent is MainComponent.Child.SE,
        ),
    )

    val containerColor = MaterialTheme.colorScheme.background
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onBackground

    PermanentNavigationDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.width(200.dp),
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
                    is MainComponent.Child.Dashboard -> DashboardScreen(child.component)
                    is MainComponent.Child.Database -> DatabaseScreen(child.component)
                    is MainComponent.Child.CE -> ComingSoonScreen(child.component)
                    is MainComponent.Child.Project -> ProjectListScreen(
                        child.component,
                        Modifier.fillMaxSize()
                    )
                    is MainComponent.Child.SE -> ComingSoonScreen(child.component)
                    is MainComponent.Child.ComingSoon -> ComingSoonScreen(child.component)
                }
            }
        }
    )
}