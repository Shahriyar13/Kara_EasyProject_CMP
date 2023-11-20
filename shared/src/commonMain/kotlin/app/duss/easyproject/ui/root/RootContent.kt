package app.duss.easyproject.ui.root

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.filled.SwipeUp
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.duss.easyproject.ui.comingsoon.ComingSoonScreen
import app.duss.easyproject.ui.favorite.FavoriteScreen
import app.duss.easyproject.ui.helper.LocalSafeArea
import app.duss.easyproject.ui.main.DashboardScreen
import app.duss.easyproject.ui.project.ProjectScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
internal fun RootContent(component: RootComponent) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSafeArea.current)
    ) {
        Menu(
            component = component,
            modifier = Modifier.fillMaxHeight().requiredWidth(150.dp).padding(all = 8.dp)
        )

        Children(
            stack = component.childStack,
            animation = stackAnimation(fade()),
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.Dashboard -> DashboardScreen(child.component)
                is RootComponent.Child.Database -> FavoriteScreen(child.component)
                is RootComponent.Child.CE -> ComingSoonScreen(child.component)
                is RootComponent.Child.Project -> ProjectScreen(child.component)
                is RootComponent.Child.SE -> ComingSoonScreen(child.component)
                is RootComponent.Child.ComingSoon -> ComingSoonScreen(child.component)
            }
        }
    }


}

@Composable
private fun Menu(component: RootComponent, modifier: Modifier = Modifier) {
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
            isSelected = activeComponent is RootComponent.Child.Dashboard,
        ),
        MenuItemData(
            title = "Database",
            icon = Icons.Default.DataArray,
            onClick = component::onDatabaseTabClicked,
            isSelected = activeComponent is RootComponent.Child.Database,
        ),
        MenuItemData(
            title = "Customer Enquiry",
            icon = Icons.Default.RequestQuote,
            onClick = component::onCETabClicked,
            isSelected = activeComponent is RootComponent.Child.CE,
        ),
        MenuItemData(
            title = "Project",
            icon = Icons.Default.Dashboard,
            onClick = component::onProjectTabClicked,
            isSelected = activeComponent is RootComponent.Child.Project,
        ),
        MenuItemData(
            title = "Supplier Enquiry",
            icon = Icons.Default.RequestQuote,
            onClick = component::onSETabClicked,
            isSelected = activeComponent is RootComponent.Child.SE,
        ),
    )

    PermanentNavigationDrawer(
        drawerContent = {
            menuItems.forEach {
                NavigationDrawerItem(
                    label = { Text(it.title) },
                    selected = it.isSelected,
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.title,
                        )
                    },
                    onClick = it.onClick,
                )
            }
        },
        modifier = modifier
    ) {

    }
}