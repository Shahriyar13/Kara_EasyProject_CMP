package app.duss.easyproject.presentation.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.ui.dashboard.components.DashboardContent

@Composable
internal fun DashboardScreen(component: DashboardComponent) {

    val state by component.state.collectAsState()

    Scaffold { paddingValues ->
        DashboardContent(
            state = state,
            onEvent = component::onEvent,
            onOutput = component::onOutput,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
