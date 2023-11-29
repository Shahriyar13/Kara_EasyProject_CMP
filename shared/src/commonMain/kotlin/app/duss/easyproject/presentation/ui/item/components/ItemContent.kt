package app.duss.easyproject.presentation.ui.item.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.item.ItemComponent
import app.duss.easyproject.presentation.ui.item.store.ItemStore
import app.duss.easyproject.presentation.ui.project.list.components.PokemonGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ItemContent(
    state: ItemStore.State,
    onEvent: (ItemStore.Intent) -> Unit,
    onOutput: (ItemComponent.Output) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        modifier = Modifier.padding(LocalSafeArea.current)
    ) {  paddingValue ->
        Box(
            modifier = Modifier.padding(paddingValue)
        ) {

            state.error?.let { error ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = error)
                }
            }

            Column {
                Text(
                    text = "Database",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp, bottom = 6.dp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = .4f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )

                if (state.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (state.projectList.isEmpty()) {
                    Text(
                        text = "Your favorite list is empty!",
                        color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(20.dp)
                    )
                } else {
                    PokemonGrid(
                        onPokemonClicked = { name ->
                            onOutput(ItemComponent.Output.NavigateToCustomerDetails(id = null))
                        },
                        projectList = state.projectList,
                        isLoading = state.isLoading,
                    )
                }
            }
        }
    }
}