package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Quotation(
    val supplierEnquiry: SupplierEnquiry?, //TODO
    var quotationItems: List<QuotationItem> = listOf(),
    val fileAttachments: List<FileAttachment> = listOf(),
    var confirmTime: Long? = null,
    override val code: String? = null,
    override val codeExtension: String? = null,
    override val annualId: Int? = null,
    override val time: Long? = null,
    override val sendTime: Long? = null,
    override val id: Long? = null,
    override val creationTime: Long? = null,
    override val modificationTime: Long? = null,
    override val createdBy: String? = null,
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null,
): BaseDocumentEntity()
