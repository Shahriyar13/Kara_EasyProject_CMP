package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Item
import kotlinx.serialization.Serializable

@Serializable
class ItemRequest (
    var id: Long? = null,
    var name: String = "",
    var note: String? = null,
    var parentItem: ItemRequest? = null,
    val subItems: List<ItemRequest>? = ArrayList(),
)

fun Item.toDto(): ItemRequest = ItemRequest(
    id = id,
    name = name,
    note = note,
    parentItem = parentItem?.toDto(),
    subItems = subItems?.map { it.toDto() }
)