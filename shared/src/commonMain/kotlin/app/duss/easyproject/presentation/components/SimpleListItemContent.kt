package app.duss.easyproject.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SimpleListItemContent(
    onClick: () -> Unit,
    title: String,
    subtitle: String? = null,
    caption: String? = null,
    brush: Brush,
    modifier: Modifier = Modifier,
) {

    Card(
        onClick = {
            onClick.invoke()
        },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            draggedElevation = 0.dp,
            disabledElevation = 0.dp,

            ),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(brush = brush, alpha = .4f)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
//            AsyncImage(
//                url = project.imageUrl,
//                contentDescription = project.name,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(1.2f)
//                    .fillMaxHeight()
//            )

//            Spacer(Modifier.height(14.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.background(Color.Transparent)
            )

            subtitle?.let {
                Spacer(Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.background(Color.Transparent)
                )
            }

        }
    }
}