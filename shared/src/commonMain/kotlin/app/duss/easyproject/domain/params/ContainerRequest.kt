package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Container
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

fun Container.toDto() = ContainerRequest(
    id = id,
    code = code,
    note = note,
    plate = plate,
    freightPrice = freightPrice,
    freightTax = freightTax,
    boxesIds = boxes.map { it.id!! },
)