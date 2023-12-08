package app.duss.easyproject.presentation.forms

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectCreateRequest
import app.duss.easyproject.domain.params.ProjectUpdateRequest

data class ProjectForm(
    val project: Project,
    var title: String? = project.title,
    var sendTime: Long? = project.sendTime,
    var code: String = project.code,
    var codeExtension: String? = project.codeExtension,
    var time: Long = project.time,
    var annualId: Int = project.annualId,
    var customerEnquiriesIds: List<Long> = project.customerEnquiries.mapNotNull { it.id },
) {
    fun hasUnsavedChanges() =
        (project.title != title) ||
                (project.sendTime != sendTime) ||
                (project.code != code) ||
                (project.codeExtension != codeExtension) ||
                (project.time != time) ||
                (project.annualId != annualId) ||
                (project.customerEnquiries.mapNotNull { it.id } != customerEnquiriesIds)

    fun toUpdateRequest() = ProjectUpdateRequest(
        id = project.id!!,
        code = code,
        title = title,
        annualId = annualId,
        codeExtension = codeExtension,
        time = time,
        customerEnquiriesIds = customerEnquiriesIds
    )

    fun toCreateRequest() = ProjectCreateRequest(
        code = code,
        title = title,
        annualId = annualId,
        codeExtension = codeExtension,
        time = time,
        customerEnquiriesIds = customerEnquiriesIds
    )
}