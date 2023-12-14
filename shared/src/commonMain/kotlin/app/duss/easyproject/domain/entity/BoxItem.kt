package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class BoxItem (
        var quotationItem: QuotationItem,
        var quantity: Int,
        val code: String?,
        override var id: Long?,
        override var creationTime: Long?,
        override var modificationTime: Long?,
        override var createdBy: String?,
        override var modifiedBy: String?,
        override var creatorId: Long?,
        override var modifierId: Long?
): BaseEntity()
