package app.duss.easyproject.presentation.ui.landing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.landing.components.LandingContent

@Composable
internal fun LandingScreen(component: LandingComponent) {
    val state by component.state.collectAsState()

    LandingContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput
    )
}