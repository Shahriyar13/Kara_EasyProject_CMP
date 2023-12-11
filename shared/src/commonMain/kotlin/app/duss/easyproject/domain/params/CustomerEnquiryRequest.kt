package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.CustomerEnquiry
import kotlinx.serialization.Serializable

@Serializable
class CustomerEnquiryRequest (
    var id: Long? = null,
    val title: String?,
    val code: String?,
    val codeExtension: String?,
    val annualId: Int?,
    val customerBuyerId: Long,
    val customerConsigneeId: Long?,
    val customerEndUserId: Long?,
    val projectId: Long?,
    val time: Long,
    val customerEnquiryItems: List<CustomerEnquiryItemRequest>? = ArrayList(),
)

fun CustomerEnquiry.toDto() = CustomerEnquiryRequest(
    id = id,
    code = code,
    title = title,
    annualId = annualId,
    codeExtension = codeExtension,
    time = time,
    customerBuyerId = customerBuyer.id!!,
    customerConsigneeId = customerConsignee?.id,
    customerEndUserId = customerEndUser?.id,
    projectId = projectId,
    customerEnquiryItems = customerEnquiryItems.map { it.toDto() },
)