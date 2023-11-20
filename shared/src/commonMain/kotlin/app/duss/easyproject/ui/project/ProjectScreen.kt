package app.duss.easyproject.ui.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.ui.project.components.ProjectContent

@Composable
internal fun ProjectScreen(component: ProjectComponent) {

    val state by component.state.collectAsState()

    ProjectContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput
    )

}