package app.duss.easyproject.presentation.ui.project.list.components

import androidx.compose.runtime.Composable
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.presentation.component.PagingVerticalGrid
import app.duss.easyproject.presentation.ui.project.components.ProjectItem
import app.duss.easyproject.presentation.ui.project.components.ProjectLoadingItem
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStore

@Composable
internal fun ProjectListContent(
    state: ProjectListStore.State,
    onEvent: (ProjectListStore.Intent) -> Unit,
    onOutput: (ProjectListComponent.Output) -> Unit,
) {
    PagingVerticalGrid(
        content = { item: Project ->
            ProjectItem(
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
            ProjectLoadingItem(alpha = it)
        }
    )
}
