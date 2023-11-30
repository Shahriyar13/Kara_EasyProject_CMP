package app.duss.easyproject.presentation.ui.project.pane.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsScreen
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import app.duss.easyproject.presentation.ui.project.list.ProjectListScreen
import app.duss.easyproject.presentation.ui.project.pane.ProjectComponent
import app.duss.easyproject.presentation.ui.project.pane.store.ProjectStore
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
internal fun ProjectContent(
    component: ProjectComponent,
    state: ProjectStore.State,
    onEvent: (ProjectStore.Intent) -> Unit,
    onOutput: (ProjectComponent.Output) -> Unit,
) {

    val childStack by component.childStack.subscribeAsState()

    val activeComponent = childStack.active.instance


    val saveableStateHolder = rememberSaveableStateHolder()

    val listPane: @Composable (Child.Created<*, ProjectListComponent>) -> Unit =
        remember {
            movableContentOf { (config, component) ->
                saveableStateHolder.SaveableStateProvider(key = config.hashCode()) {
                    ProjectListScreen(
                        component = component,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }

    val detailsPane: @Composable (Child.Created<*, ProjectDetailsComponent>) -> Unit =
        remember {
            movableContentOf { (config, component) ->
                saveableStateHolder.SaveableStateProvider(key = config.hashCode()) {
                    ProjectDetailsScreen(
                        component = component,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }

    saveableStateHolder.OldDetailsKeyRemoved(selectedDetailsKey = children.detailsChild?.configuration?.hashCode())

    BoxWithConstraints(modifier = Modifier) {
        Children(
            stack = component.childStack,
            animation = stackAnimation(fade()),
        ) {
            when (val child = it.instance) {
                is ProjectComponent.Children.List -> ProjectListScreen(
                    child.component,
                    Modifier.fillMaxSize()
                )
                is ProjectComponent.Children.Details -> ProjectDetailsScreen(
                    child.component,
                    Modifier.fillMaxSize()
                )
            }
        }
        when {
            state.isMultiPane ->
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxHeight().weight(0.4F)) {
                        ProjectListScreen(child.component, Modifier.fillMaxSize())
                    }

                    Box(modifier = Modifier.fillMaxHeight().weight(0.6F)) {
                        children.detailsChild?.also {
                            detailsPane(it)
                        }
                    }
                }

            detailsChild != null -> detailsPane(detailsChild)
            else -> listPane(listChild)
        }

        val isMultiPaneRequired = this@BoxWithConstraints.maxWidth >= 800.dp

        DisposableEffect(isMultiPaneRequired) {
            onEvent(ProjectStore.State)
            state.setMultiPane(isMultiPaneRequired)
            onDispose {}
        }
    }
}

@Composable
private fun SaveableStateHolder.OldDetailsKeyRemoved(selectedDetailsKey: Any?) {
    var lastDetailsKey by remember { mutableStateOf(selectedDetailsKey) }

    if (selectedDetailsKey == lastDetailsKey) {
        return
    }

    val keyToRemove = lastDetailsKey
    lastDetailsKey = selectedDetailsKey

    if (keyToRemove == null) {
        return
    }

    DisposableEffect(keyToRemove) {
        removeState(key = keyToRemove)
        onDispose {}
    }
}