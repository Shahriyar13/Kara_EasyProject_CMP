package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class StateRequest(
    var id: Long? = null,
    val title: String,
    val countryId: Long?,
    val cities: List<CityRequest>? = arrayListOf()
)
