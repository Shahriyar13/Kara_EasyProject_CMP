package app.duss.easyproject.presentation.ui.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.item.components.ItemContent

@Composable
internal fun ItemScreen(component: ItemComponent) {
    val state by component.state.collectAsState()

    ItemContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput
    )
}