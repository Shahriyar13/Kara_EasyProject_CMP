package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class ContainerRequest(
    val id: Long? = null,
    var code: String?,
    var note: String?,
    var plate: String?,
    var freightPrice: Double?,
    var freightTax: Double?,
    var boxesIds: List<Long>,
)