package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Project


class ProjectCreateRequest(
    val code: String,
    val title: String?,
    val annualId: Int,
    val codeExtension: String?,
    val time: Long,
    val customerEnquiriesIds: List<Long>,
)

fun Project.toCreateRequest() = ProjectCreateRequest(
    code = code,
    title = title,
    annualId = annualId,
    codeExtension = codeExtension,
    time = time,
    customerEnquiriesIds = customerEnquiries.mapNotNull { it.id }
)