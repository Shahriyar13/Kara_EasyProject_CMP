package app.duss.easyproject.presentation.ui

import androidx.compose.runtime.Composable
import app.duss.easyproject.presentation.ui.root.RootComponent
import app.duss.easyproject.presentation.ui.root.RootScreen

@Composable
internal fun ContentView(
    component: RootComponent,
) {
    RootScreen(component)
}