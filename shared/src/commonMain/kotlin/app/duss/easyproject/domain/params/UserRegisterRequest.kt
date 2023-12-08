package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.enums.Title
import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterRequest(
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
