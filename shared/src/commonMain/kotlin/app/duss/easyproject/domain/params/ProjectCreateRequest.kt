package app.duss.easyproject.domain.params


class ProjectCreateRequest(
    val code: String,
    val title: String?,
    val annualId: Int,
    val codeExtension: String?,
    val time: Long,
    val customerEnquiriesIds: List<Long>,
)