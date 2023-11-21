package app.duss.easyproject.ui.project.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.duss.easyproject.ui.helper.LocalSafeArea
import app.duss.easyproject.ui.project.ProjectComponent
import app.duss.easyproject.ui.project.store.ProjectStore
import app.duss.easyproject.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProjectContent(
    state: ProjectStore.State,
    onEvent: (ProjectStore.Intent) -> Unit,
    onOutput: (ProjectComponent.Output) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Projects") },
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


//                Divider(
//                    color = MaterialTheme.colorScheme.outline.copy(alpha = .4f),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp)
//                )

                if (state.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = .6f),
                        trackColor = MaterialTheme.colorScheme.outline.copy(alpha = .4f),
                    )
                }

                PokemonGrid(
                    onPokemonClicked = { name ->
                        onOutput(ProjectComponent.Output.NavigateToDetails(id = null))
                    },
                    projectList = state.projectList,
                    isLoading = !state.isLastPageLoaded,
                    loadMoreItems = {
                        if (state.projectList.isEmpty()) return@PokemonGrid

                        val nextPage = state.projectList.last().page + 1
                        onEvent(ProjectStore.Intent.LoadProjectListByPage(page = nextPage))
                    }
                )
            }


        }
    }
}