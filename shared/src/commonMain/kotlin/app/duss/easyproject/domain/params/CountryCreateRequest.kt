package app.duss.easyproject.domain.params

data class CountryCreateRequest(
    val title: String,
    val states: List<StateCreateRequest>? = arrayListOf()
)
