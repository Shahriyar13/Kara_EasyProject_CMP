package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class RegionCustomsPortRequest(
    val id: Long? = null,
    val name: String,
    val cityId: Long? = null,
    val countryId: Long? = null,
)
