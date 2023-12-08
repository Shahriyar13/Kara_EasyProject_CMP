package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.enums.Title
import kotlinx.serialization.Serializable

@Serializable
data class PersonUpdateRequest(
    val id: Long,
    val title: String? = Title.Unknown.name,
    val firstName: String? = null,
    val lastName: String? = null,
    val jobTitle: String? = null,
    val companyId: Long? = null,
    val email: String? = null,
    val telephone: String? = null,
    val mobile: String? = null,
)
