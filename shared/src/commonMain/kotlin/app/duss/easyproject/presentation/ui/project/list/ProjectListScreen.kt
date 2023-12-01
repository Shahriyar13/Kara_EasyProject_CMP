package app.duss.easyproject.presentation.ui.project.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.project.list.components.ProjectListContent

@Composable
internal fun ProjectListScreen(component: ProjectListComponent) {

    val state by component.state.collectAsState()

    ProjectListContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput,
    )

}