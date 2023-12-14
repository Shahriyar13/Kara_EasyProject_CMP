package app.duss.easyproject.presentation.ui.project.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsScreen
import app.duss.easyproject.presentation.ui.project.list.components.ProjectListContent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
internal fun ProjectListScreen(component: ProjectListComponent) {

    val state by component.state.collectAsState()

    Children(
        component.childStack,
    ) {
        when (val child = it.instance) {
            is ProjectListComponent.Child.Details -> ProjectDetailsScreen(child.component)
            ProjectListComponent.Child.None -> ProjectListContent(
                state = state,
                onEvent = component::onEvent,
                onOutput = component::onOutput,
            )
        }
    }

}