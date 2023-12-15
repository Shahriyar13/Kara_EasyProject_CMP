package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Quotation
import kotlinx.serialization.Serializable

@Serializable
data class QuotationRequest (
    val id: Long? = null,
    val code: String?,
    val time: Long?,
    val confirmTime: Long?,
    val quotationItems: List<QuotationItemRequest> = ArrayList(),
    val supplierEnquiryId: Long,
)

fun Quotation.toDto() = QuotationRequest(
    id = id,
    code = code,
    supplierEnquiryId = supplierEnquiry!!.id!!,
    time = time,
    confirmTime = sendTime,
    quotationItems = quotationItems.map { it.toDto() },
)