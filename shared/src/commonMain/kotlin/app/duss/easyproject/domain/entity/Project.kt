package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    var title: String? = null,
    val customerEnquiries: List<CustomerEnquiry> = emptyList(),
    override val sendTime: Long? = null,
    override var code: String,
    override val codeExtension: String? = null,
    override val time: Long,
    override val annualId: Int,
    override val id: Long? = null,
    override val creationTime: Long = 0,
    override val modificationTime: Long? = null,
    override val createdBy: String = "",
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null
): BaseDocumentEntity()