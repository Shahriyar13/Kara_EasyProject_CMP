package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class BoxOfItem (
        var code: String? = null,
        var weightGross: Double? = null,
        var length: Double? = null,
        var width: Double? = null,
        var height: Double? = null,
        var boxItems: List<BoxItem> = listOf(),
        override val id: Long? = null,
        override val creationTime: Long? = null,
        override val modificationTime: Long? = null,
        override val createdBy: String? = null,
        override val modifiedBy: String? = null,
        override val creatorId: Long? = null,
        override val modifierId: Long? = null,
): BaseEntity()
