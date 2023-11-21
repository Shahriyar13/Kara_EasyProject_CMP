package app.duss.easyproject.domain.entity

import app.duss.easyproject.domain.base.BaseDocumentEntity

data class CustomerEnquiry(
    val title: String?,
    val customerBuyer: Customer,
    val customerConsignee: Customer?,
    val customerEndUser: Customer?,
    override val code: String,
    override val codeExtension: String,
    override val time: Long,
    override val sendTime: Long?,
    override val annualId: Int,
    override val id: Long,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?

): BaseDocumentEntity(
    annualId = annualId,
    code = code,
    codeExtension = codeExtension,
    time = time,
    sendTime = sendTime,
    id = id,
    creationTime = creationTime,
    modificationTime = modificationTime,
    createdBy = createdBy,
    modifiedBy = modifiedBy,
    creatorId = creatorId,
    modifierId = modifierId
)