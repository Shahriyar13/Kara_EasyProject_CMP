package app.duss.easyproject.presentation.ui.multipane.list

import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.duss.easyproject.presentation.component.PagingVerticalGrid
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
internal fun <T> ArticleListContent(
    component: ArticleListComponent<T>,
    itemContent: @Composable (T, () -> Unit) -> Unit,
    itemLoadingContent: @Composable (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val model: ArticleListComponent.Model<T> by component.models.subscribeAsState()

    PagingVerticalGrid(
        content = { item ->
            itemContent(item) {
                component.onArticleClicked(item)
            }
        },
        itemList = model.items,
        isLoading = model.isLoading,
        loadMoreItems = {
            if (model.items.isEmpty()) return@PagingVerticalGrid
            val nextPage = model.page + 1
            model.page = nextPage
            component.loadNextPage(nextPage)
        },
        loadContent = { itemLoadingContent(it) },
        modifier = modifier,
    )
}

@Composable
private fun selectionColor(): Color =
    MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
