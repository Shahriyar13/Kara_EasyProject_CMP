package app.duss.easyproject.presentation.ui.project.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.BaseDocumentEntity
import app.duss.easyproject.presentation.utils.PokemonAbilityUtils

@Composable
internal fun AbilityRow(
    types: List<BaseDocumentEntity>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(types, key = { it.code ?: "" }) { typeResponse ->
            AbilityItem(
                name = typeResponse.code ?: "",
                containerColor = PokemonAbilityUtils.getAbilityColor(typeResponse.code ?: "")
            )
        }
    }
}