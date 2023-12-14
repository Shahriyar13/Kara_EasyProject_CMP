package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class CustomerEnquiryItem(
    val item: Item,
    var quantity: Int = 1,
    var note: String? = null,
    val fileAttachments: List<FileAttachment> = emptyList(),
    override val id: Long? = null,
    override val creationTime: Long? = null,
    override val modificationTime: Long? = null,
    override val createdBy: String? = null,
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null,
): BaseEntity()
