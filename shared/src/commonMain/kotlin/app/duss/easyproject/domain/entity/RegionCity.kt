package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class RegionCity(
    val name: String,
    val id: Long?,
    val state: RegionState? = null,
)