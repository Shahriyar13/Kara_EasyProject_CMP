package app.duss.easyproject.presentation.ui.ce.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.ui.ce.details.components.CEDetailsContent

@Composable
internal fun CEDetailsScreen(component: CEDetailsComponent, modifier: Modifier = Modifier) {

    val state by component.state.collectAsState()

    CEDetailsContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput,
        modifier = modifier,
    )

}