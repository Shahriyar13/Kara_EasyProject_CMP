package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.RegionCustomsPort
import kotlinx.serialization.Serializable

@Serializable
data class RegionCustomsPortRequest(
    val id: Long? = null,
    val name: String,
)

fun RegionCustomsPort.toDto() = RegionCustomsPortRequest(
    id = id,
    name = name,
)