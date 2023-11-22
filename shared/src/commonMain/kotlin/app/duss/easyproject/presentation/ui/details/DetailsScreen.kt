package app.duss.easyproject.presentation.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.details.components.DetailsContent

@Composable
internal fun DetailsScreen(component: ProjectDetailsComponent) {

    val state by component.state.collectAsState()

    DetailsContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput
    )

}