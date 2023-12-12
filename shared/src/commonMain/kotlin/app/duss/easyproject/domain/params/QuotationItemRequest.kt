package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class QuotationItemRequest (
    val id: Long?,
    val customerEnquiryItemId: Long,
    var quantity: Int,
    var price: Double? = 0.0,
    var tax: Double? = 0.0,
    var weightNet: Double? = 0.0,
    var note: String? = null,
)