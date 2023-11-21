package app.duss.easyproject.domain.base

abstract class BaseNamedEntity (
    open val name: String,
    override val id: Long,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?,
): BaseEntity(
    id = id,
    creationTime = creationTime,
    modificationTime = modificationTime,
    createdBy = createdBy,
    modifiedBy = modifiedBy,
    creatorId = creatorId,
    modifierId = modifierId
)