package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SupplierEnquiry(
    val project: Project,
    val supplier: Company,
    val customerEnquiryItems: List<CustomerEnquiryItem> = ArrayList(),
    val fileAttachments: List<FileAttachment> = ArrayList(),
    val peopleInCharge: List<Person>,
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
