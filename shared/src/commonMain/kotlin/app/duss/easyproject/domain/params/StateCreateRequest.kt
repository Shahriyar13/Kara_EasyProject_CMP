package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class StateCreateRequest(
    val title: String,
    val countryId: Long?,
    val cities: List<CityCreateRequest>? = arrayListOf()
)
