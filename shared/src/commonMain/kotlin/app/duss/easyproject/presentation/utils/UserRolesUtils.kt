package app.duss.easyproject.presentation.utils

import androidx.compose.ui.graphics.Color
import app.duss.easyproject.domain.enums.Role
import app.duss.easyproject.presentation.theme.Bug
import app.duss.easyproject.presentation.theme.Fighting
import app.duss.easyproject.presentation.theme.Flying
import app.duss.easyproject.presentation.theme.Ghost
import app.duss.easyproject.presentation.theme.Ground
import app.duss.easyproject.presentation.theme.Poison
import app.duss.easyproject.presentation.theme.Rock

object UserRolesUtils {

     fun getRoleColor(role: Role): Color = when(role) {
         Role.NOT_ACTIVE -> Fighting
         Role.BLOCKED -> Flying
         Role.VIEWER -> Poison
         Role.COLLECTOR -> Ground
         Role.EDITOR -> Rock
         Role.MANAGER -> Bug
         Role.ADMIN -> Ghost
     }
}