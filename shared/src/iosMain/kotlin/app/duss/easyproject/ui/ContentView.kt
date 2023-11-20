package app.duss.easyproject.ui

import androidx.compose.runtime.Composable
import app.duss.easyproject.ui.root.RootComponent
import app.duss.easyproject.ui.root.RootContent

@Composable
internal fun ContentView(
    component: RootComponent,
) {
    RootContent(component)
}