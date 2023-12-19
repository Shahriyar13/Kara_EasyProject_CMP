package app.duss.easyproject.presentation.ui.packing.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.ui.packing.details.components.PackingDetailsContent

@Composable
internal fun PackingDetailsScreen(component: PackingDetailsComponent, modifier: Modifier = Modifier) {

    val state by component.state.collectAsState()

    PackingDetailsContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput,
        component = component,
        modifier = modifier,
    )

}