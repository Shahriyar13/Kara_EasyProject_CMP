package app.duss.easyproject.presentation.ui.quotation.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.ui.quotation.details.components.QuotationDetailsContent

@Composable
internal fun QuotationDetailsScreen(component: QuotationDetailsComponent, modifier: Modifier = Modifier) {

    val state by component.state.collectAsState()

    QuotationDetailsContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput,
        component = component,
        modifier = modifier,
    )

}