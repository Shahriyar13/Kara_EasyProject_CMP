package app.duss.easyproject.presentation.ui.ce.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.ce.details.CEDetailsScreen
import app.duss.easyproject.presentation.ui.ce.list.components.CEListContent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
internal fun ProjectListScreen(component: CEListComponent) {

    val state by component.state.collectAsState()

    Children(
        component.childStack,
    ) {
        when (val child = it.instance) {
            is CEListComponent.Child.Details -> CEDetailsScreen(child.component)
            CEListComponent.Child.None -> CEListContent(
                state = state,
                onEvent = component::onEvent,
                onOutput = component::onOutput,
            )
        }
    }

}