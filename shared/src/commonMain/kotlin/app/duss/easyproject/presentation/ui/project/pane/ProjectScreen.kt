package app.duss.easyproject.presentation.ui.project.pane

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.project.pane.components.ProjectContent

@Composable
internal fun ProjectScreen(
    component: ProjectComponent,
) {
    val state by component.state.collectAsState()
    
    ProjectContent(
        state,
        component::onEvent,
        component::onOutput
    )

}