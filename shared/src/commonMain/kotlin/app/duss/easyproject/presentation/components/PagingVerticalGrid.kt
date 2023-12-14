package app.duss.easyproject.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.duss.easyproject.data.network.NetworkConstants.PageSize

@Composable
internal fun <T> PagingVerticalGrid(
    itemList: List<T>,
    isLoading: Boolean,
    loadMoreItems: () -> Unit = {},
    loadSize: Int = PageSize,
    modifier: Modifier = Modifier,
    columns: GridCells = GridCells.Fixed(1),
    loadContent: @Composable (Float) -> Unit,
    content: @Composable (T) -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = .1f,
        targetValue = .4f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column {
        if (!isLoading && itemList.isEmpty()) {
            Text(
                text = "Your list is empty!",
                color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(20.dp)
            )
        }
            LazyVerticalGrid(
                columns = columns,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(20.dp),
                modifier = modifier,
            ) {
                items(itemList) { item ->
                    content(item)
                }

                if (isLoading) {
                    items(loadSize) { index ->
                        LaunchedEffect(Unit) {
                            if (index == 0) loadMoreItems()
                        }

                        loadContent(alpha)
                    }
                }
            }
        }
}