package app.duss.easyproject.presentation.ui.project.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.Project

@Composable
internal fun PokemonInfos(
    project: Project,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.outline.copy(.2f))
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Icon(
                    Icons.Outlined.Scale,
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))

                val weightInKg = 100 / 10f

                Text(
                    text = "$weightInKg kg",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Weight",
                color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(color = MaterialTheme.colorScheme.outline)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Icon(
                    Icons.Outlined.Straighten,
                    contentDescription = null,
                    modifier = Modifier.rotate(90f)
                )
                Spacer(Modifier.width(4.dp))

                val heightInMeter = 100 / 10f

                Text(
                    text = "$heightInMeter m",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Height",
                color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}