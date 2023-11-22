package app.duss.easyproject.domain.entity

import app.duss.easyproject.domain.enums.Title
import kotlinx.serialization.Serializable

@Serializable
data class Person (
    val title: Title? = Title.Unknown,
    val firstName: String?,
    val lastName: String?,
    val jobTitle: String?,
    val email: String?,
    val telephone: String?,
    val mobile: String?,
    override val id: Long,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?
): BaseEntity() {
    fun fullName() = "${if (title != null && title != Title.Unknown) "${title.name}. " else ""}${if (firstName.isNullOrEmpty()) "" else "$firstName "}${if (lastName.isNullOrEmpty()) "" else lastName}"
}
