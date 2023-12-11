package app.duss.easyproject.presentation.ui.ce.list.components

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.presentation.utils.toFormattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CEItemContents(
    onClick: () -> Unit,
    item: CustomerEnquiry,
    brush: Brush,
    modifier: Modifier = Modifier,
) {

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
                text = item.customerBuyer.company.name + " - " + ((item.title?.takeIf { it.isNotBlank() }) ?: "No title"),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.alpha(.8f)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = item.time.toFormattedDate(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.alpha(.4f)
            )
        }
    }
}