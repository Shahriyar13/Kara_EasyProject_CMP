package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class CustomerEnquiryItem(
    val item: Item,
    var quantity: Int,
    var note: String?,
    val fileAttachments: List<FileAttachment>,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?
): BaseEntity()