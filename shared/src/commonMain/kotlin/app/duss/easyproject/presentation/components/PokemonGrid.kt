package app.duss.easyproject.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.Project
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
import app.duss.easyproject.presentation.ui.project.list.components.ProjectItem
import app.duss.easyproject.presentation.ui.project.list.components.ProjectLoadingItem

@Composable
internal fun PokemonGrid(
    onPokemonClicked: (name: String) -> Unit,
    projectList: List<Project>,
    isLoading: Boolean,
    loadMoreItems: () -> Unit = {},
    modifier: Modifier = Modifier,
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

    BoxWithConstraints {
        val columns = when(maxWidth) {
            in 0.dp..349.dp -> 1
            in 350.dp..599.dp -> 2
            in 600.dp..899.dp -> 3
            in 900.dp..1199.dp -> 4
            else -> 5
        }

        var index = 0

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(20.dp),
            modifier = modifier,
        ) {
            items(projectList, key = { it.code }) { pokemon ->
                index++
                ProjectItem(
                    onClick = { onPokemonClicked(pokemon.code) },
                    project = pokemon,
                    brush = remember(index) {
                        val colorIndex = index % colors.size
                        Brush.linearGradient(colors[colorIndex])
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (isLoading) {
                items(5) { index ->
                    LaunchedEffect(Unit) {
                        if (index == 0) loadMoreItems()
                    }

                    ProjectLoadingItem(alpha = alpha)
                }
            }

        }
    }
}