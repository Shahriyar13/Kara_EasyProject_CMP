package app.duss.easyproject.presentation.ui.project.signlepane.components

import androidx.compose.runtime.Composable
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsScreen
import app.duss.easyproject.presentation.ui.project.list.ProjectListScreen
import app.duss.easyproject.presentation.ui.project.signlepane.ProjectSinglePaneComponent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation

@Composable
internal fun ProjectSinglePaneContent(
    component: ProjectSinglePaneComponent,
) {

    Children(
        stack = component.childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is ProjectSinglePaneComponent.Children.Details -> ProjectDetailsScreen(child.component)
            is ProjectSinglePaneComponent.Children.List -> ProjectListScreen(child.component)
        }
    }
}