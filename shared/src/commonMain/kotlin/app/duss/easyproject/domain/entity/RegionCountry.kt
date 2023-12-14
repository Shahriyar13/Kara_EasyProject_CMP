package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class RegionCountry(
    val id: Long?,
    val name: String,
    val code: String,
    val region: String,
    val subregion: String,
    val phoneCode: String,
    val nationality: String,
    val emoji: String,
    val emojiU: String,
    val states: List<RegionState>?,
)