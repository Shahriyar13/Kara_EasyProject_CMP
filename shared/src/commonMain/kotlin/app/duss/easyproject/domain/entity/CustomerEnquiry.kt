package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class CustomerEnquiry(
    val title: String?,
    val customerBuyer: Company,
    val customerConsignee: Company?,
    val customerEndUser: Company?,
    val customerEnquiryItems: List<CustomerEnquiryItem>,
    val peopleInCharge: List<Person>,
    val fileAttachments: List<FileAttachment>,
    val project: Project,
    override var code: String,
    override val codeExtension: String,
    override val time: Long,
    override val sendTime: Long?,
    override val annualId: Int,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?
): BaseDocumentEntity()