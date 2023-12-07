package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Project


class ProjectUpdateRequest(
    val id: Long,
    val code: String,
    val title: String?,
    val annualId: Int,
    val codeExtension: String?,
    val time: Long,
    val customerEnquiriesIds: List<Long>,
)

fun Project.toUpdateRequest() = ProjectUpdateRequest(
    id = id!!,
    code = code,
    title = title,
    annualId = annualId,
    codeExtension = codeExtension,
    time = time,
    customerEnquiriesIds = customerEnquiries.mapNotNull { it.id }
)