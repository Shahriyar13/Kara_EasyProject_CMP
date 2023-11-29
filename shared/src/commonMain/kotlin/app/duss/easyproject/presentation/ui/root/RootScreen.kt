package app.duss.easyproject.presentation.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.ui.root.components.RootContent

@Composable
internal fun RootScreen(component: RootComponent) {
    RootContent(
        component = component,
        modifier = Modifier.fillMaxSize()
    )
}