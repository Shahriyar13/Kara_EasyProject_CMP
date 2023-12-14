package app.duss.easyproject.data.network.dto

import app.duss.easyproject.domain.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginDto(
        val token: String,
        val user: User,
)
