package app.duss.easyproject.domain.base

abstract class BaseEntity (
    open val id: Long,
    open val creationTime: Long,
    open val modificationTime: Long?,
    open val createdBy: String,
    open val modifiedBy: String?,
    open val creatorId: Long?,
    open val modifierId: Long?,
)