package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class QuotationItem(
    val customerEnquiryItem: CustomerEnquiryItem,
    var price: Double = 0.0,
    var tax: Double = 0.0,
    var weightNet: Double = 0.0,
    var quantity: Int = customerEnquiryItem.quantity,
    var note: String? = null,
    val fileAttachments: List<FileAttachment> = listOf(),
    override val id: Long? = null,
    override val creationTime: Long? = null,
    override val modificationTime: Long? = null,
    override val createdBy: String? = null,
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null,
): BaseEntity()
