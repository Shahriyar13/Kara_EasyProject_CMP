package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    var note: String?,
    var parentItem: Item?,
    val subItems: List<Item>? = emptyList(),
    val type: String? = null,
    val modelNo: String? = null,
    val unit: String? = "PCs",
    val hsCodeEu: String? = null,
    override val name: String,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?
): BaseNamedEntity()