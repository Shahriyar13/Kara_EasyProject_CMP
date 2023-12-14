package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class UserChangeRoleRequest(
    val username: String,
    val role: String,
)
