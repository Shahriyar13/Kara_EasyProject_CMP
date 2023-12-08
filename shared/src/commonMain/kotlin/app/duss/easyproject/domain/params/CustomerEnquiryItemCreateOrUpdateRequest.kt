package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
class CustomerEnquiryItemCreateOrUpdateRequest (
    val id: Long?,
    val customerEnquiryId: Long,
    val itemId: Long?,
    val item: ItemCreateRequest?,
    var quantity: Int,
    var note: String? = null,
)