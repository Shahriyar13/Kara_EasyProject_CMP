package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.CustomerEnquiry
import kotlinx.serialization.Serializable

@Serializable
class CustomerEnquiryRequest (
    val id: Long? = null,
    val title: String?,
    val code: String?,
    val codeExtension: String?,
    val annualId: Int?,
    val customerBuyerId: Long,
    val customerConsigneeId: Long?,
    val customerEndUserId: Long?,
    val peopleInCharges: List<PersonRequest>,
    val projectId: Long?,
    val time: Long?,
    val sendTime: Long?,
    val customerEnquiryItems: List<CustomerEnquiryItemRequest>? = ArrayList(),
)

fun CustomerEnquiry.toDto() = CustomerEnquiryRequest(
    id = id,
    code = code,
    title = title,
    annualId = annualId,
    codeExtension = codeExtension,
    time = time,
    customerBuyerId = customerBuyer?.id ?: throw (Exception("Buyer Customer is required")),
    customerConsigneeId = customerConsignee?.id,
    customerEndUserId = customerEndUser?.id,
    projectId = project?.id,
    sendTime = sendTime,
    customerEnquiryItems = customerEnquiryItems.map { it.toDto() },
    peopleInCharges = peopleInCharges.map { it.toDto() },
)