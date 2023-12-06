package app.duss.easyproject.presentation.ui.project.list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.presentation.component.PagingVerticalGrid
import app.duss.easyproject.presentation.ui.project.components.ProjectItem
import app.duss.easyproject.presentation.ui.project.components.ProjectLoadingItem
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProjectListContent(
    state: ProjectListStore.State,
    onEvent: (ProjectListStore.Intent) -> Unit,
    onOutput: (ProjectListComponent.Output) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Projects") },
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
    ) {
        PagingVerticalGrid(
            modifier = Modifier.padding(it),
            content = { item: Project ->
                ProjectItem(
                    project = item,
                    onClick = {
                        onEvent(ProjectListStore.Intent.Details(item.id!!))
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
            loadContent = { alpha ->
                ProjectLoadingItem(alpha = alpha)
            }
        )
    }
}
