package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class CityCreateRequest(
    val title: String,
    val stateId: Long?,
)
