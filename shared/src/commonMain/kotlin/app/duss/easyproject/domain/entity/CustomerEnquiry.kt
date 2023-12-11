package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class CustomerEnquiry(
    var title: String?,
    val customerBuyer: Customer,
    val customerConsignee: Customer?,
    val customerEndUser: Customer?,
    val customerEnquiryItems: List<CustomerEnquiryItem>,
    var projectId: Long?,
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