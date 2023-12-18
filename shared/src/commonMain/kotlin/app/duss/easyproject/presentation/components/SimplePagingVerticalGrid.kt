package app.duss.easyproject.presentation.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import app.duss.easyproject.data.network.NetworkConstants.PageSize
import app.duss.easyproject.presentation.components.Constants.colorsOfListItems

@Composable
internal fun <T> SimplePagingVerticalGrid(
    itemList: List<T>,
    isLoading: Boolean,
    isLastPageLoaded: Boolean,
    loadMoreItems: () -> Unit = {},
    loadSize: Int = PageSize,
    modifier: Modifier = Modifier,
    columns: GridCells = GridCells.Fixed(1),
    content: @Composable (T, Brush) -> Unit,
) {

    var index = -1
    PagingVerticalGrid(
        modifier = modifier,
        content = { item: T ->
            index++
            content(
                item,
                remember(index) {
                    val colorIndex = index % colorsOfListItems.size
                    Brush.linearGradient(colorsOfListItems[colorIndex])
                }
            )
        },
        itemList = itemList,
        isLoading = isLoading,
        loadMoreItems = {
           loadMoreItems()
        },
        isLastPageLoaded = isLastPageLoaded,
        loadContent = { alpha ->
            SimpleListItemLoadingContent(alpha = alpha)
        }
    )
}