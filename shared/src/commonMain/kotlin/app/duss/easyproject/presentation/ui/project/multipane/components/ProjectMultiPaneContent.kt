package app.duss.easyproject.presentation.ui.project.multipane.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.presentation.component.PagingVerticalGrid
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.project.components.ProjectItem
import app.duss.easyproject.presentation.ui.project.components.ProjectLoadingItem
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsScreen
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStore
import app.duss.easyproject.presentation.ui.project.multipane.ProjectMultiPaneComponent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProjectMultiPaneContent(
    component: ProjectMultiPaneComponent,
) {

    val state by component.state.collectAsState()
    val onEvent: (ProjectListStore.Intent) -> Unit = component::onEvent
    val onOutput: (ProjectMultiPaneComponent.Output) -> Unit = component::onOutput

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "m Projects") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(onClick = {
                        component.onNewItemClicked()
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

            Row {
                PagingVerticalGrid(
                    content = { item: Project ->
                        ProjectItem(
                            project = item,
                            onClick = {
                                component.onItemClicked(item.id!!)
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
                        ProjectLoadingItem(
                            alpha = it
                        )
                    },
                    modifier = Modifier.fillMaxHeight().weight(1F)
                )

                Children(
                    component.childStack
                ) {
                    when (val child = it.configuration) {
                        is ProjectMultiPaneComponent.Children.Details -> ProjectDetailsScreen(child.component)
                        ProjectMultiPaneComponent.Children.None -> Unit
                    }
                }
            }
        }
    }
}