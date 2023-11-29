package app.duss.easyproject.core.network.model

import app.duss.easyproject.domain.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginDto(
        val token: String,
        val user: User,
)
