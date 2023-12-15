package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.CustomerEnquiryItem
import kotlinx.serialization.Serializable

@Serializable
class CustomerEnquiryItemRequest(
    val id: Long?,
    val item: ItemRequest?,
    var quantity: Int,
    var note: String? = null,
)

fun CustomerEnquiryItem.toDto() = CustomerEnquiryItemRequest(
    id = id,
    item = item.toDto(),
    quantity = quantity,
    note = note,
)
