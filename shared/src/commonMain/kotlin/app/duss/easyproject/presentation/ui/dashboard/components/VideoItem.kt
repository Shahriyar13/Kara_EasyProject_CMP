package app.duss.easyproject.presentation.ui.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.FileAttachment
import app.duss.easyproject.presentation.components.AsyncImage
import app.duss.easyproject.presentation.theme.Black
import app.duss.easyproject.presentation.utils.toFormattedDateTime

@Composable
internal fun VideoItem(
    onClick: () -> Unit,
    fileAttachment: FileAttachment,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .width(220.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                url = fileAttachment.url,
                contentDescription = fileAttachment.getFileName(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Black.copy(.5f), BlendMode.Darken),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clip(MaterialTheme.shapes.medium)
            )

            Icon(
                Icons.Rounded.PlayArrow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp),
            )

            Icon(
                Icons.Outlined.Circle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(60.dp),
            )
        }

        Text(
            text = fileAttachment.getFileName(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleSmall,

        )

        Text(
            text = fileAttachment.creationTime.toFormattedDateTime(),
            color = MaterialTheme.colorScheme.onBackground.copy(.8f),
            style = MaterialTheme.typography.bodyMedium,
        )
    }

}