package app.duss.easyproject.presentation.ui.database

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.company.CompanyScreen
import app.duss.easyproject.presentation.ui.item.ItemScreen
import app.duss.easyproject.presentation.ui.person.PersonScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DatabaseScreen(
    component: DatabaseComponent,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Database") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        modifier = Modifier.padding(LocalSafeArea.current),
        bottomBar = {
            BottomAppBar {
                BottomNavigationItem(selected = false, onClick = { }, icon = { })
            }
        }
    ) { paddingValue ->
        Children(
            modifier = Modifier.padding(paddingValue),
            stack = component.childStack
        ) {
            when (val child = it.instance) {
                is DatabaseComponent.Child.CompanyChild -> CompanyScreen(child.component)
                is DatabaseComponent.Child.ItemChild -> ItemScreen(child.component)
                is DatabaseComponent.Child.PersonChild -> PersonScreen(child.component)
            }
        }
    }
}