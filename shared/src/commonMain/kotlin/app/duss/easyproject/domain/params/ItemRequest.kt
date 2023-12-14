package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Item
import kotlinx.serialization.Serializable

@Serializable
class ItemRequest (
    val id: Long? = null,
    val type: String? = null,
    val modelNo: String? = null,
    val unit: String? = "PCs",
    val hsCodeEu: String? = null,

    var name: String = "",
    var note: String? = null,
    var parentItem: ItemRequest? = null, // Reference to the parent item
    val subItems: List<ItemRequest>? = ArrayList()
)

fun Item.toDto(): ItemRequest = ItemRequest(
    id = id,
    name = name,
    note = note,
    parentItem = parentItem?.toDto(),
    subItems = subItems?.map { it.toDto() }
)