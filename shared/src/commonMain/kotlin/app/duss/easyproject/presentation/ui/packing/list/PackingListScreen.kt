package app.duss.easyproject.presentation.ui.packing.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.packing.details.PackingDetailsScreen
import app.duss.easyproject.presentation.ui.packing.list.components.PackingListContent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
internal fun PackingListScreen(component: PackingListComponent) {

    val state by component.state.collectAsState()

    Children(
        component.childStack,
    ) {
        when (val child = it.instance) {
            is PackingListComponent.Child.Details -> PackingDetailsScreen(child.component)
            PackingListComponent.Child.None -> PackingListContent(
                state = state,
                onEvent = component::onEvent,
                onOutput = component::onOutput,
            )
        }
    }

}