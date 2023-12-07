package app.duss.easyproject.domain.params

class CustomerEnquiryUpdateRequest (
    val id: Long,
    val title: String?,
    val code: String?,
    val codeExtension: String?,
    val annualId: Int?,
    val customerBuyerId: Long,
    val customerConsigneeId: Long?,
    val customerEndUserId: Long?,
    val projectId: Long?,
    val time: Long,
    val customerEnquiryItems: List<CustomerEnquiryItemCreateOrUpdateRequest>? = ArrayList(),
)
