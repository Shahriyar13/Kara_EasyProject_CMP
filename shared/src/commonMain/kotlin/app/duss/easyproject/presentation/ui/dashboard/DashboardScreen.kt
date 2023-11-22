package app.duss.easyproject.presentation.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.ui.dashboard.components.MainContent

@Composable
internal fun DashboardScreen(component: DashboardComponent) {

    val state by component.state.collectAsState()

    Scaffold { paddingValues ->
        MainContent(
            state = state,
            onEvent = component::onEvent,
            onOutput = component::onOutput,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
