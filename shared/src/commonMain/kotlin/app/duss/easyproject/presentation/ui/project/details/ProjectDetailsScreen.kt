package app.duss.easyproject.presentation.ui.project.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.ui.project.details.components.DetailsContent

@Composable
internal fun ProjectDetailsScreen(component: ProjectDetailsComponent, modifier: Modifier = Modifier) {

    val state by component.state.collectAsState()

    DetailsContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput,
        modifier = modifier,
    )

}