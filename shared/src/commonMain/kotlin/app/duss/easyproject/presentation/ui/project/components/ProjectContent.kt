package app.duss.easyproject.presentation.ui.project.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.ui.project.ProjectComponent
import app.duss.easyproject.presentation.ui.project.multipane.ProjectMultiPaneScreen
import app.duss.easyproject.presentation.ui.project.signlepane.ProjectSinglePaneScreen
import app.duss.easyproject.presentation.ui.project.store.ProjectStore
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation

@Composable
internal fun ProjectContent(
    component: ProjectComponent,
) {

    val onEvent: (ProjectStore.Intent) -> Unit = component::onEvent


    BoxWithConstraints {
            Children(
                stack = component.childStack,
                animation = stackAnimation(fade()),
            ) {
                when (val child = it.instance) {
                    is ProjectComponent.Children.SinglePane -> ProjectSinglePaneScreen(child.component)
                    is ProjectComponent.Children.MultiPane -> ProjectMultiPaneScreen(child.component)
                }
            }
        val isMultiPaneRequired = this@BoxWithConstraints.maxWidth > 800.dp

        DisposableEffect(isMultiPaneRequired) {
            onEvent(if (isMultiPaneRequired) ProjectStore.Intent.MultiPane else ProjectStore.Intent.SinglePane)
            onDispose {  }
        }
    }
}