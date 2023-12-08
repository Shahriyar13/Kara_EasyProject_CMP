package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class StateUpdateRequest(
    val id: Long,
    val title: String,
    val countryId: Long,
)
