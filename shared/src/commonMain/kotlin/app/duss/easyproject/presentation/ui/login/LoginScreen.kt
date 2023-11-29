package app.duss.easyproject.presentation.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.login.components.LoginContent

@Composable
internal fun LoginScreen(component: LoginComponent) {
    val state by component.state.collectAsState()

    LoginContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput
    )
}