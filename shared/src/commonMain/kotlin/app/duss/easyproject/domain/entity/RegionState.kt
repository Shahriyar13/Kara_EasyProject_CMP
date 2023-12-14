package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class RegionState(
    val id: Long?,
    val name: String,
    val cities: List<RegionCity>? = arrayListOf(),
    val country: RegionCountry? = null,
)