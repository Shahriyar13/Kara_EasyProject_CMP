package app.duss.easyproject.presentation.utils

import androidx.compose.ui.graphics.Color
import app.duss.easyproject.presentation.theme.*

object PokemonAbilityUtils {

     fun getAbilityColor(name: String): Color = when(name) {
         "fighting" -> Fighting
         "flying" -> Flying
         "poison" -> Poison
         "ground" -> Ground
         "rock" -> Rock
         "bug" -> Bug
         "ghost" -> Ghost
         "steel" -> Steel
         "fire" -> Fire
         "water" -> Water
         "grass" -> Grass
         "electric" -> Electric
         "psychic" -> Psychic
         "ice" -> Ice
         "dragon" -> Dragon
         "fairy" -> Fairy
         "dark" -> Dark
         else -> Gray400
     }

}