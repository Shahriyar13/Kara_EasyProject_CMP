package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Quotation(
    val isConfirmedByCustomer: Boolean,
    val supplierEnquiry: SupplierEnquiry,
    val quotationItems: List<QuotationItem> = ArrayList(),
    val fileAttachments: List<FileAttachment> = ArrayList(),
    override val code: String,
    override val codeExtension: String?,
    override val annualId: Int,
    override val time: Long,
    override val sendTime: Long?,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?,
): BaseDocumentEntity()
