package app.duss.easyproject.presentation.ui.database

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.database.components.DatabaseContent

@Composable
internal fun DatabaseScreen(component: DatabaseComponent) {
    val state by component.state.collectAsState()

    DatabaseContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput
    )
}