package app.duss.easyproject.domain.entity

import app.duss.easyproject.domain.enums.Title
import kotlinx.serialization.Serializable

@Serializable
data class Person (
    val title: Title? = Title.Unknown,
    var firstName: String? = null,
    var lastName: String? = null,
    var jobTitle: String? = null,
    var email: String? = null,
    var telephone: String? = null,
    var mobile: String? = null,
    val company: Company? = null,
    var individualCompanyName: String? = null,
    override val id: Long? = null,
    override val creationTime: Long? = null,
    override val modificationTime: Long? = null,
    override val createdBy: String? = null,
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null,
): BaseEntity() {
    fun fullName() = "${if (title != null && title != Title.Unknown) "${title.name}. " else ""}${if (firstName.isNullOrEmpty()) "" else "$firstName "}${if (lastName.isNullOrEmpty()) "" else lastName}"
}
