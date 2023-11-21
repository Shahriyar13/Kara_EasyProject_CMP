package app.duss.easyproject.presentation.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.duss.easyproject.ui.project.components.PokemonLoadingItem

@Composable
internal fun <T> PagingVerticalGrid(
    itemList: List<T>,
    isLoading: Boolean,
    loadMoreItems: () -> Unit = {},
    loadSize: Int = 5,
    modifier: Modifier = Modifier,
    columns: GridCells = GridCells.Fixed(1),
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

    BoxWithConstraints {
        LazyVerticalGrid(
            columns = columns,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(20.dp),
            modifier = modifier,
        ) {
            items(itemList, ) { item ->
                content(item)
            }

            if (isLoading) {
                items(loadSize) { index ->
                    LaunchedEffect(Unit) {
                        if (index == 0) loadMoreItems()
                    }

                    PokemonLoadingItem(alpha = alpha)
                }
            }

        }
    }
}