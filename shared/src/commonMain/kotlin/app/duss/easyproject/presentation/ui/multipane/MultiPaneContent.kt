package app.duss.easyproject.presentation.ui.multipane

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
import app.duss.easyproject.presentation.ui.multipane.details.ArticleDetailsComponent
import app.duss.easyproject.presentation.ui.multipane.list.ArticleListComponent
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
internal fun <T> MultiPaneContent(
    component: MultiPaneComponent<T>,
    listItemContent: @Composable (ArticleListComponent<T>) -> Unit,
    detailsItemContent: @Composable (ArticleDetailsComponent<T>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val children by component.children.subscribeAsState()
    val listChild = children.listChild
    val detailsChild = children.detailsChild

    val saveableStateHolder = rememberSaveableStateHolder()

    val listPane: @Composable (Child.Created<*, ArticleListComponent<T>>) -> Unit =
        remember {
            movableContentOf { (config, component) ->
                saveableStateHolder.SaveableStateProvider(key = config.hashCode()) {
                    listItemContent(component)
                }
            }
        }

    val detailsPane: @Composable (Child.Created<*, ArticleDetailsComponent<T>>) -> Unit =
        remember {
            movableContentOf { (config, component) ->
                saveableStateHolder.SaveableStateProvider(key = config.hashCode()) {
                    detailsItemContent(component)
                }
            }
        }

    saveableStateHolder.OldDetailsKeyRemoved(selectedDetailsKey = children.detailsChild?.configuration?.hashCode())

    BoxWithConstraints(modifier = modifier) {
        when {
            children.isMultiPane ->
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxHeight().weight(0.4F)) {
                        listPane(children.listChild)
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
            component.setMultiPane(isMultiPaneRequired)
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
