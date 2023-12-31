package app.duss.easyproject.domain.params

data class StateCreateRequest(
    val title: String,
    val countryId: Long?,
    val cities: List<CityCreateRequest>? = arrayListOf()
)
