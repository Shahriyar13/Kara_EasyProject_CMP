package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class RegionCustomsPort(
    val name: String,
    val id: Long?,
)