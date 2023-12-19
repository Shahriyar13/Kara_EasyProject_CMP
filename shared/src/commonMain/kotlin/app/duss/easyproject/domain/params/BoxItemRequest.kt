package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.BoxItem
import kotlinx.serialization.Serializable

@Serializable
data class BoxItemRequest(
    val id: Long? = null,
    var quotationItemId: Long,
    var quantity: Int,
    val code: String?,
)

fun BoxItem.toDto() = BoxItemRequest(
    id = id,
    quotationItemId = quotationItem.id!!,
    quantity = quantity,
    code = code
)