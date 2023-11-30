package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable


@Serializable
data class Customer(
    val code: String,
    val company: Company,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?
): BaseEntity()