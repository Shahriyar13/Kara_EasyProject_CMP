package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class BoxItem (
        var quotationItem: QuotationItem,
        var quantity: Int,
        val code: String? = null,
        override var id: Long? = null,
        override var creationTime: Long? = null,
        override var modificationTime: Long? = null,
        override var createdBy: String? = null,
        override var modifiedBy: String? = null,
        override var creatorId: Long? = null,
        override var modifierId: Long? = null,
): BaseEntity()
