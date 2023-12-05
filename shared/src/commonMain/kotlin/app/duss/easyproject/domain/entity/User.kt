package app.duss.easyproject.domain.entity

import app.duss.easyproject.domain.enums.Role
import app.duss.easyproject.domain.enums.Title
import kotlinx.serialization.Serializable


@Serializable
data class User (
    var username: String,
    val title: Title? = Title.Unknown,
    val firstName: String? = null,
    val lastName: String? = null,
    val jobTitle: String? = null,
    val email: String? = null,
    val telephone: String? = null,
    val mobile: String? = null,
    var role: Role = Role.NOT_ACTIVE,
    override val id: Long?,
    override val creationTime: Long = -1,
    override val modificationTime: Long? = null,
    override val createdBy: String = "Unknown",
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null
    ): BaseEntity() {
    fun fullName() = "${if (title != null && title != Title.Unknown) "${title.name}. " else ""}${if (firstName.isNullOrEmpty()) "" else "$firstName "}${if (lastName.isNullOrEmpty()) "" else lastName}"
}
