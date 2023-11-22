package app.duss.easyproject.presentation.ui.dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.ui.dashboard.DashboardComponent
import app.duss.easyproject.presentation.ui.dashboard.state.CategoryState
import app.duss.easyproject.presentation.ui.dashboard.store.MainStore

@Composable
internal fun MainContent(
    state: MainStore.State,
    onEvent: (MainStore.Intent) -> Unit,
    onOutput: (DashboardComponent.Output) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        CategoryState.database to DashboardComponent.Output.DatabaseClicked,
        CategoryState.ce to DashboardComponent.Output.ComingSoonClicked,
        CategoryState.project to DashboardComponent.Output.ProjectClicked,
        CategoryState.se to DashboardComponent.Output.ComingSoonClicked,
        CategoryState.sq to DashboardComponent.Output.ComingSoonClicked,
        CategoryState.pi to DashboardComponent.Output.ComingSoonClicked,
        CategoryState.po to DashboardComponent.Output.ComingSoonClicked,
        CategoryState.shipping to DashboardComponent.Output.ComingSoonClicked,
        CategoryState.invoice to DashboardComponent.Output.ComingSoonClicked,
        CategoryState.bafa to DashboardComponent.Output.ComingSoonClicked,
        CategoryState.payment to DashboardComponent.Output.ComingSoonClicked,
        CategoryState.profile to DashboardComponent.Output.ComingSoonClicked,
    )


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        item(span = { GridItemSpan(2) }) {
            Text(
                text = "What Pokemon are you looking for ?",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            )
        }

        item(span = { GridItemSpan(2) }) {
            val containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .2f)
            TextField(
                value = state.search,
                onValueChange = { onEvent(MainStore.Intent.InputPokemonSearch(it)) },
                placeholder = {
                    Text(text = "Search Pokemon")
                },
                leadingIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Rounded.Search, contentDescription = "Search Pokemon")
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.surface,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.surface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                ),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )
        }
        items(categories) { category ->
            CategoryButton(
                onClick = { onOutput(category.second) },
                modifier = modifier.padding(all = 8.dp),
                categoryState = category.first,
            )
        }
    }
}