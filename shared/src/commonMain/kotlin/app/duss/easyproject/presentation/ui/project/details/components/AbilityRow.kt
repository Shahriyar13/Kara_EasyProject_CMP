package app.duss.easyproject.presentation.ui.project.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.presentation.utils.UserRolesUtils

@Composable
internal fun AbilityRow(
    managers: List<User>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(managers, key = { it.id ?: "" }) { manager ->
            AbilityItem(
                name = manager.fullName(),
                containerColor = UserRolesUtils.getRoleColor(manager.role)
            )
        }
    }
}