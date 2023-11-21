package app.duss.easyproject.ui.project.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.duss.easyproject.core.model.Project
import app.duss.easyproject.presentation.component.PagingVerticalGrid
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

                if (state.isLoading && state.projectList.isEmpty()) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = .6f),
                        trackColor = MaterialTheme.colorScheme.outline.copy(alpha = .4f),
                    )
                }

                PagingVerticalGrid(
                    content = { item: Project -> ProjectItem(
                        project = item,
                        onClick = {
                            onOutput(ProjectComponent.Output.NavigateToDetails(id = null))
                        },
                        )
                    },
                    itemList = state.projectList,
                    isLoading = !state.isLastPageLoaded,
                    loadSize = 5,
                    loadMoreItems = {
                        if (state.projectList.isEmpty()) return@PagingVerticalGrid

                        val nextPage = state.projectList.last().page + 1
                        onEvent(ProjectStore.Intent.LoadProjectListByPage(page = nextPage))
                    }
                )

                if (state.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = .6f),
                        trackColor = MaterialTheme.colorScheme.outline.copy(alpha = .4f),
                    )
                }
            }
        }
    }
}