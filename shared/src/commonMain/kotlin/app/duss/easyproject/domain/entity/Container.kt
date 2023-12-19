package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Container (
    var code: String?,
    var note: String?,
    var plate: String?,
    var freightPrice: Double?,
    var freightTax: Double?,
    var boxes: List<BoxOfItem>,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?
): BaseEntity()
