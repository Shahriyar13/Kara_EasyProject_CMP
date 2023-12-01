package app.duss.easyproject.presentation.ui.project.multipane

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.ui.project.multipane.components.ProjectMultiPaneContent

@Composable
internal fun ProjectMultiPaneScreen(component: ProjectMultiPaneComponent, modifier: Modifier) {

    ProjectMultiPaneContent(
        component = component,
    )

}