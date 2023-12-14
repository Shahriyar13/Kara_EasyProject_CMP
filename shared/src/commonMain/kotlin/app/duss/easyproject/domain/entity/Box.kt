package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Box (
        var code: String?,
        var weightGross: Double?,
        var length: Double?,
        var width: Double?,
        var height: Double?,
        var boxItems: List<BoxItem>,
        override val id: Long?,
        override val creationTime: Long,
        override val modificationTime: Long?,
        override val createdBy: String,
        override val modifiedBy: String?,
        override val creatorId: Long?,
        override val modifierId: Long?
): BaseEntity()
