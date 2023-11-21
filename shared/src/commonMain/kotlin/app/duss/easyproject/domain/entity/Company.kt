package app.duss.easyproject.domain.entity

import app.duss.easyproject.domain.base.BaseNamedEntity

data class Company(
    val symbol: String?,
    override val name: String,
    override val id: Long,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?

): BaseNamedEntity(
    name = name,
    id = id,
    creationTime = creationTime,
    modificationTime = modificationTime,
    createdBy = createdBy,
    modifiedBy = modifiedBy,
    creatorId = creatorId,
    modifierId = modifierId
)