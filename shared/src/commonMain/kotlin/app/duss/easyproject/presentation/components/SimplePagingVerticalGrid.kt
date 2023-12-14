package app.duss.easyproject.presentation.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import app.duss.easyproject.data.network.NetworkConstants.PageSize
import app.duss.easyproject.presentation.theme.Blue300
import app.duss.easyproject.presentation.theme.Blue500
import app.duss.easyproject.presentation.theme.Green300
import app.duss.easyproject.presentation.theme.Green500
import app.duss.easyproject.presentation.theme.Indigo300
import app.duss.easyproject.presentation.theme.Indigo500
import app.duss.easyproject.presentation.theme.Orange300
import app.duss.easyproject.presentation.theme.Orange500
import app.duss.easyproject.presentation.theme.Pink300
import app.duss.easyproject.presentation.theme.Pink500
import app.duss.easyproject.presentation.theme.Purple300
import app.duss.easyproject.presentation.theme.Purple500
import app.duss.easyproject.presentation.theme.Red300
import app.duss.easyproject.presentation.theme.Red500
import app.duss.easyproject.presentation.theme.Teal300
import app.duss.easyproject.presentation.theme.Teal500
import app.duss.easyproject.presentation.theme.Yellow300
import app.duss.easyproject.presentation.theme.Yellow500

@Composable
internal fun <T> SimplePagingVerticalGrid(
    itemList: List<T>,
    isLoading: Boolean,
    loadMoreItems: () -> Unit = {},
    loadSize: Int = PageSize,
    modifier: Modifier = Modifier,
    columns: GridCells = GridCells.Fixed(1),
    content: @Composable (T, Brush) -> Unit,
) {

    val colors = listOf(
        listOf(Green300, Green500),
        listOf(Red300, Red500),
        listOf(Blue300, Blue500),
        listOf(Purple300, Purple500),
        listOf(Orange300, Orange500),
        listOf(Teal300, Teal500),
        listOf(Pink300, Pink500),
        listOf(Indigo300, Indigo500),
        listOf(Yellow300, Yellow500),
    )

    var index = 0
    PagingVerticalGrid(
        modifier = modifier,
        content = { item: T ->
            index++
            content(
                item,
                remember(index) {
                    val colorIndex = index % colors.size
                    Brush.linearGradient(colors[colorIndex])
                }
            )
        },
        itemList = itemList,
        isLoading = isLoading,
        loadMoreItems = {
           loadMoreItems()
        },
        loadContent = { alpha ->
            SimpleListItemLoadingContent(alpha = alpha)
        }
    )
}