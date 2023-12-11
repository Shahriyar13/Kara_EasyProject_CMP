package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class CountryRequest(
    val id: Long? = null,
    val title: String,
    val states: List<StateRequest>? = arrayListOf()
)
