package app.duss.easyproject.ui.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.ui.pokedex.PokedexComponent
import app.duss.easyproject.ui.pokedex.components.KCommerceContent

@Composable
internal fun PokedexScreen(component: PokedexComponent) {

    val state by component.state.collectAsState()

    KCommerceContent(
        state = state,
        onEvent = component::onEvent,
        onOutput = component::onOutput
    )

}