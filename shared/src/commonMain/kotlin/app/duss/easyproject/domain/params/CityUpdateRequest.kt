package app.duss.easyproject.domain.params

data class CityUpdateRequest(
    val id: Long,
    val title: String,
    val stateId: Long,
)
