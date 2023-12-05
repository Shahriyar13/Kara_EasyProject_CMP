package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.enums.Title

data class UserUpdateRequest(
    val id: Long,
    val username: String,
    val password: String,
    val firstName: String?,
    val lastName: String,
    val email: String?,
    val mobile: String?,
    val telephone: String?,
    val title: String? = Title.Unknown.name,
    val jobTitle: String? = null,
)
