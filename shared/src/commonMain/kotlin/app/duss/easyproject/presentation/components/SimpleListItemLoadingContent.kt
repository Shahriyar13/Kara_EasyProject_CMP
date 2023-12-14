package app.duss.easyproject.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

@Composable
internal fun SimpleListItemLoadingContent(
    modifier: Modifier = Modifier,
    alpha: Float,
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                this.alpha = abs(1f - alpha)
            }
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.primary.copy(.6f))
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.padding(12.dp)
        ) {

            val firstBoxWidthFraction = remember {
                Random.nextFloat()
            }
            Box(
                modifier = modifier
                    .padding(top = 2.dp)
                    .fillMaxWidth(max(.3f, firstBoxWidthFraction))
                    .height(20.dp)
                    .graphicsLayer {
                        this.alpha = alpha
                    }
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.onBackground.copy(.4f))
            )

            val secondBoxWidthFraction = remember {
                Random.nextFloat()
            }
            Box(
                modifier = modifier
                    .padding(bottom = 2.dp)
                    .fillMaxWidth(max(.3f, secondBoxWidthFraction))
                    .height(14.dp)
                    .graphicsLayer {
                        this.alpha = alpha
                    }
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.onBackground.copy(.4f))
            )
        }
    }
}