package app.duss.easyproject.presentation.ui.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.duss.easyproject.presentation.ui.favorite.components.FavoriteContent

@Composable
internal fun FavoriteScreen(databaseComponent: DatabaseComponent) {
    val state by databaseComponent.state.collectAsState()

    FavoriteContent(
        state = state,
        onEvent = databaseComponent::onEvent,
        onOutput = databaseComponent::onOutput
    )
}