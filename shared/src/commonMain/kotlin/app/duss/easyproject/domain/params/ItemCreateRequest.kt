package app.duss.easyproject.domain.params

class ItemCreateRequest (
    var name: String = "",
    var note: String? = null,
    var parentItem: ItemCreateRequest? = null, // Reference to the parent item
    val subItems: List<ItemCreateRequest> = ArrayList()
)