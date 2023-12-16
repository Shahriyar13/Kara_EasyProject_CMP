package app.duss.easyproject.presentation.ui.quotation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.quotation.details.QuotationDetailsScreen
import app.duss.easyproject.presentation.ui.quotation.list.components.QuotationListContent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
internal fun QuotationListScreen(component: QuotationListComponent) {

    val state by component.state.collectAsState()

    Children(
        component.childStack,
    ) {
        when (val child = it.instance) {
            is QuotationListComponent.Child.Details -> QuotationDetailsScreen(child.component)
            QuotationListComponent.Child.None -> QuotationListContent(
                state = state,
                onEvent = component::onEvent,
                onOutput = component::onOutput,
            )
        }
    }

}