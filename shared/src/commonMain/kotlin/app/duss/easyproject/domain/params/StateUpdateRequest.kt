package app.duss.easyproject.domain.params

data class StateUpdateRequest(
    val id: Long,
    val title: String,
    val countryId: Long,
)
