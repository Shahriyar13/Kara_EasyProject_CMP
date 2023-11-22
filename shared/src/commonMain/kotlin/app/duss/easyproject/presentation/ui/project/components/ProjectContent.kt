package app.duss.easyproject.presentation.ui.project.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.presentation.component.PagingVerticalGrid
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.theme.*
import app.duss.easyproject.presentation.ui.project.ProjectComponent
import app.duss.easyproject.presentation.ui.project.store.ProjectStore

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
    ) { paddingValue ->
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

            PagingVerticalGrid(
                content = { item: Project ->
                    ProjectItem(
                        project = item,
                        onClick = {
                            onOutput(ProjectComponent.Output.NavigateToDetails(id = null))
                        },
                    )
                },
                itemList = state.projectList,
                isLoading = !state.isLastPageLoaded,
                loadMoreItems = {
                    if (state.projectList.isEmpty()) return@PagingVerticalGrid
                    val nextPage = state.page + 1
                    onEvent(ProjectStore.Intent.LoadProjectListByPage(page = nextPage))
                },
                loadContent = {
                    ProjectLoadingItem(alpha = it)
                }
            )
        }
    }
}