package app.duss.easyproject.presentation.ui.database

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.company.CompanyScreen
import app.duss.easyproject.presentation.ui.item.ItemScreen
import app.duss.easyproject.presentation.ui.person.PersonScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
internal fun DatabaseScreen(
    component: DatabaseComponent,
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance
    val containerColor = MaterialTheme.colorScheme.background
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onBackground

    class MenuItemData(
        val title: String,
        val icon: ImageVector,
        val onClick: () -> Unit,
        val isSelected: Boolean,
    )

    val menuItems = listOf(
        MenuItemData(
            title = "Company",
            icon = Icons.Default.PrecisionManufacturing,
            onClick = component::onCompanyTabClicked,
            isSelected = activeComponent is DatabaseComponent.Child.CompanyChild,
        ),
        MenuItemData(
            title = "Person",
            icon = Icons.Default.Person,
            onClick = component::onPersonTabClicked,
            isSelected = activeComponent is DatabaseComponent.Child.PersonChild,
        ),
        MenuItemData(
            title = "Item",
            icon = Icons.Default.Engineering,
            onClick = component::onItemTabClicked,
            isSelected = activeComponent is DatabaseComponent.Child.ItemChild,
        ),
    )
    Scaffold(
        modifier = Modifier.padding(LocalSafeArea.current),
        bottomBar = {
            BottomAppBar (
                containerColor = containerColor,
            ) {
               menuItems.map {
                   BottomNavigationItem(
                       label = {
                           Text(
                               it.title,
                               softWrap = false,
                               color = if (it.isSelected) selectedColor else unselectedColor
                           )
                       },
                       selectedContentColor = selectedColor,
                       unselectedContentColor = unselectedColor,
                       selected = activeComponent is DatabaseComponent.Child.CompanyChild,
                       onClick = it.onClick,
                       icon = {
                           Icon(
                               imageVector = it.icon,
                               contentDescription = it.title,
                               tint = if (it.isSelected) selectedColor else unselectedColor
                           )
                       }
                   )
               }
            }
        }
    ) { paddingValue ->
        Children(
            modifier = Modifier.padding(paddingValue),
            stack = component.childStack,
        ) {
            when (val child = it.instance) {
                is DatabaseComponent.Child.CompanyChild -> CompanyScreen(child.component)
                is DatabaseComponent.Child.PersonChild -> PersonScreen(child.component)
                is DatabaseComponent.Child.ItemChild -> ItemScreen(child.component)
            }
        }
    }
}