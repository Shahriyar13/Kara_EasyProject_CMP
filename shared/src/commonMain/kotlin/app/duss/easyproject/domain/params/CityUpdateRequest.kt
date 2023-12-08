package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class CityUpdateRequest(
    val id: Long,
    val title: String,
    val stateId: Long,
)
