package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    var note: String? = null,
    var parentItem: Item? = null,
    val subItems: List<Item>? = emptyList(),
    val type: String? = null,
    val modelNo: String? = null,
    val unit: String? = null,
    val hsCodeEu: String? = null,
    override val name: String = "",
    override val id: Long? = null,
    override val creationTime: Long? = null,
    override val modificationTime: Long? = null,
    override val createdBy: String? = null,
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null,
): BaseNamedEntity()