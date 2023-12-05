package app.duss.easyproject.presentation.ui.root

import androidx.compose.runtime.Composable
import app.duss.easyproject.presentation.ui.root.components.RootContent

@Composable
internal fun RootScreen(component: RootComponent) {
    RootContent(
        component = component
    )
}