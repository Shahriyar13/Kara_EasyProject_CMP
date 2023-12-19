package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.BoxOfItem
import kotlinx.serialization.Serializable

@Serializable
data class BoxRequest(
    val id: Long? = null,
    var code: String?,
    var weightGross: Double?,
    var length: Double?,
    var width: Double?,
    var height: Double?,
    var boxItems: List<BoxItemRequest>,
)

fun BoxOfItem.toDto() = BoxRequest(
    id = id,
    code = code,
    weightGross = weightGross,
    length = length,
    width = width,
    height = height,
    boxItems = boxItems.map { it.toDto() },
)