package app.duss.easyproject.domain.params

class CustomerEnquiryItemCreateOrUpdateRequest (
    val id: Long?,
    val customerEnquiryId: Long,
    val itemId: Long?,
    val item: ItemCreateRequest?,
    var quantity: Int,
    var note: String? = null,
)