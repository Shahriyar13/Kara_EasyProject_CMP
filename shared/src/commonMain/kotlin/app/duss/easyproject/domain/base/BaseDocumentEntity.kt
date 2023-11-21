package app.duss.easyproject.domain.base

abstract class BaseDocumentEntity (
    open val code: String,
    open val codeExtension: String?,
    open val annualId: Int,
    open val time: Long,
    open val sendTime: Long?,
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