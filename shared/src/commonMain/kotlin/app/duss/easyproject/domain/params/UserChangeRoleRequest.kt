package app.duss.easyproject.domain.params

data class UserChangeRoleRequest(
    val username: String,
    val role: String,
)
