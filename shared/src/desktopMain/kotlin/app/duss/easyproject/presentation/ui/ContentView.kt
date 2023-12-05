package app.duss.easyproject.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.theme.AppTheme
import app.duss.easyproject.presentation.ui.root.RootComponent
import app.duss.easyproject.presentation.ui.root.RootScreen

@Composable
fun ContentView(component: RootComponent) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RootScreen(component)
        }
    }
}