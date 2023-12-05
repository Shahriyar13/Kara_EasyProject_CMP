package app.duss.easyproject.presentation.ui.company

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.company.components.CompanyContent

@Composable
internal fun CompanyScreen(companyComponent: CompanyComponent) {
    val state by companyComponent.state.collectAsState()

    CompanyContent(
        state = state,
        onEvent = companyComponent::onEvent,
        onOutput = companyComponent::onOutput,
    )
}