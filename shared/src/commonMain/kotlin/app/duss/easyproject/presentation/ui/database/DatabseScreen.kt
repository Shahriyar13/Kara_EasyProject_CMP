package app.duss.easyproject.presentation.ui.database

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.components.PokemonGrid
import app.duss.easyproject.presentation.helper.LocalSafeArea
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
            modifier = Modifier.padding(paddingValue)

        )
    }
}