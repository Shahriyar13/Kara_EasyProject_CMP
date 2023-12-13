package app.duss.easyproject.presentation.ui.project.list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.components.SimpleListItemContent
import app.duss.easyproject.presentation.components.SimplePagingVerticalGrid
import app.duss.easyproject.presentation.components.TopAppBarDocumentList
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStore
import app.duss.easyproject.presentation.utils.toFormattedDate

@Composable
internal fun ProjectListContent(
    state: ProjectListStore.State,
    onEvent: (ProjectListStore.Intent) -> Unit,
    onOutput: (ProjectListComponent.Output) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBarDocumentList(
                title = "Projects",
                onAddClicked = {
                    onEvent(ProjectListStore.Intent.AddNew)
                },
                onSearchValueChange = {
                    onEvent(ProjectListStore.Intent.UpdateSearchValue(it))
                },
                loadingState = state.isLoading,
                searchValue = state.searchValue,
            )
        },
    ) { paddingValues ->
        SimplePagingVerticalGrid(
            itemList = state.list,
            loadMoreItems = {
                if (state.list.isEmpty()) return@SimplePagingVerticalGrid
                val nextPage = state.page + 1
                onEvent(ProjectListStore.Intent.LoadByPage(page = nextPage))
            },
            isLoading = state.isLoading,
            modifier = Modifier.padding(paddingValues)
        ) { item, brush ->
            SimpleListItemContent(
                onClick = {
                    item.id?.let {
                        onEvent(ProjectListStore.Intent.Edit(id = item.id))
                    }
                },
                title = (item.code + " - " + ((item.title?.takeIf { it.isNotBlank() }?.replaceFirstChar { it.uppercase() }) ?: "No title")),
                subtitle = item.time.toFormattedDate(),
                brush = brush
            )
        }
    }
}
