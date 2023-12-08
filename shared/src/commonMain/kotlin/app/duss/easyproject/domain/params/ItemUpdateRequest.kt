package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
class ItemUpdateRequest (
    var id: Long,
    var name: String = "",
    var note: String? = null,
    var parentItemNew: ItemCreateRequest? = null,
    var parentItemUpdate: ItemUpdateRequest? = null,
    val subItemsNew: List<ItemCreateRequest> = ArrayList(),
    val subItemsUpdate: List<ItemUpdateRequest> = ArrayList(),
    val subItemsDeletedIds: List<Long> = ArrayList(),
)