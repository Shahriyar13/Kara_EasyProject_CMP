package app.duss.easyproject.presentation.ui.project.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.presentation.component.PagingVerticalGrid
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.theme.*
import app.duss.easyproject.presentation.ui.project.multipane.store.ProjectListStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProjectListContent(
    state: ProjectListStore.State,
    onEvent: (ProjectListStore.Intent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Projects") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(onClick = {
                        onEvent(ProjectListStore.Intent.AddNew)
                    }) {
                        Icon(Icons.Default.Add, "Add New one")
                    }
                }
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
                    app.duss.easyproject.presentation.ui.project.components.ProjectItem(
                        project = item,
                        onClick = {
                            onEvent(ProjectListStore.Intent.Details(item))
                        },
                    )
                },
                itemList = state.projectList,
                isLoading = !state.isLastPageLoaded,
                loadMoreItems = {
                    if (state.projectList.isEmpty()) return@PagingVerticalGrid
                    val nextPage = state.page + 1
                    onEvent(ProjectListStore.Intent.LoadProjectListByPage(page = nextPage))
                },
                loadContent = {
                    app.duss.easyproject.presentation.ui.project.components.ProjectLoadingItem(alpha = it)
                }
            )
        }
    }
}