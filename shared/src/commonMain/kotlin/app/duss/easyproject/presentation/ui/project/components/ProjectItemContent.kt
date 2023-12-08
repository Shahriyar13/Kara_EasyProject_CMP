package app.duss.easyproject.presentation.ui.project.components

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProjectItem(
    onClick: () -> Unit,
    project: Project,
    modifier: Modifier = Modifier,
) {
    val brush = remember {
        Brush.linearGradient(
            listOf(
                listOf(
                    Green300,
                    Green500,
                ),
                listOf(
                    Red300,
                    Red500,
                ),
                listOf(
                    Blue300,
                    Blue500,
                ),
                listOf(
                    Purple300,
                    Purple500,
                ),
                listOf(
                    Orange300,
                    Orange500,
                ),
                listOf(
                    Teal300,
                    Teal500,
                ),
                listOf(
                    Pink300,
                    Pink500,
                ),
                listOf(
                    Indigo300,
                    Indigo500,
                ),
                listOf(
                    Yellow300,
                    Yellow500,
                ),
            ).random()
        )
    }

    Card(
        onClick = {
            onClick.invoke()
        },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
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
                text = (project.code + " - " + (project.title?.replaceFirstChar { it.uppercase() } ?: "No title")),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.alpha(.8f)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = project.code,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.alpha(.4f)
            )
        }
    }
}