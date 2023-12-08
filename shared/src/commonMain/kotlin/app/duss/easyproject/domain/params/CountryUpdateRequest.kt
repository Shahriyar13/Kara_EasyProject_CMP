package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class CountryUpdateRequest(
    val id: Long,
    val title: String,
)
