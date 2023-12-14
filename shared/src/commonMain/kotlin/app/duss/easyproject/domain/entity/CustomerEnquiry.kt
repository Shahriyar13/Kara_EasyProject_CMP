package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class CustomerEnquiry(
    var title: String? = null,
    var customerBuyer: Company? = null,
    var customerConsignee: Company? = null,
    var customerEndUser: Company? = null,
    var customerEnquiryItems: List<CustomerEnquiryItem> = emptyList(),
    var peopleInCharges: List<Person> = emptyList(),
    var fileAttachments: List<FileAttachment> = emptyList(),
    var project: Project? = null,
    override var code: String? = null,
    override val codeExtension: String? = null,
    override val time: Long? = null,
    override val sendTime: Long? = null,
    override val annualId: Int? = null,
    override val id: Long? = null,
    override val creationTime: Long? = null,
    override val modificationTime: Long? = null,
    override val createdBy: String? = null,
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null,
): BaseDocumentEntity()