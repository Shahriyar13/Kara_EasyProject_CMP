package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.CustomerEnquiryItem
import kotlinx.serialization.Serializable

@Serializable
class CustomerEnquiryItemRequest (
    val id: Long?,
    val customerEnquiryId: Long?,
    val itemId: Long?,
    val item: ItemRequest?,
    var quantity: Int,
    var note: String? = null,
)

fun CustomerEnquiryItem.toDto() = CustomerEnquiryItemRequest(
        id = id,
        customerEnquiryId = null,
        itemId = item.id,
        item = item.toDto(),
        quantity = quantity,
        note = note,
    )
