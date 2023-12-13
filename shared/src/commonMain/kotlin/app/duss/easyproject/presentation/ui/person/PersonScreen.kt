package app.duss.easyproject.presentation.ui.person

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.person.components.PersonContent

@Composable
internal fun PersonScreen(component: PersonComponent) {
    val state by component.state.collectAsState()

    PersonContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput
    )
}