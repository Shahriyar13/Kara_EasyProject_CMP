package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class CityRequest(
    val id: Long? = null,
    val title: String,
    val stateId: Long?,
)
