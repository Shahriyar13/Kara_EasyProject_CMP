package app.duss.easyproject.presentation.ui.project.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.Project

@Composable
internal fun PokemonStats(
    project: Project,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        project.customerEnquiries?.forEach { statResponse ->
            key(statResponse.title ?: statResponse.time.toString()) {
                PokemonStatItem(
                    statResponse = statResponse,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}