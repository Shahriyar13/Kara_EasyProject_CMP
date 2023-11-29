package app.duss.easyproject.domain.params


class ProjectUpdateRequest(
    val id: Long,
    val code: String,
    val title: String?,
    val annualId: Int,
    val codeExtension: String?,
    val time: Long,
    val customerEnquiries: List<CustomerEnquiryCreateRequest> = ArrayList(),
)