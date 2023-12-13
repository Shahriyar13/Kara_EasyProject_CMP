package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Project
import kotlinx.serialization.Serializable


@Serializable
class ProjectRequest(
    val id: Long? = null,
    val code: String,
    val title: String?,
    val annualId: Int,
    val codeExtension: String?,
    val time: Long,
    val customerEnquiries: List<CustomerEnquiryRequest>,
)

fun Project.toDto() = ProjectRequest(
    id = id,
    code = code,
    title = title,
    annualId = annualId,
    codeExtension = codeExtension,
    time = time,
    customerEnquiries = customerEnquiries?.map { it.toDto() } ?: listOf()
)