package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class CountryCreateRequest(
    val title: String,
    val states: List<StateCreateRequest>? = arrayListOf()
)
