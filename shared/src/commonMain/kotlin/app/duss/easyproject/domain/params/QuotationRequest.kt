package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class QuotationRequest (
    val id: Long? = null,

    val code: String,
    val time: Long?,
    val confirmTime: Long?,
    val quotationItems: List<QuotationItemRequest> = ArrayList(),

    val supplierEnquiryId: Long,
    val projectId: Long,

    )