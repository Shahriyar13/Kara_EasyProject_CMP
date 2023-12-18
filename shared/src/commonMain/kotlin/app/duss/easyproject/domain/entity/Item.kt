package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    var note: String? = null,
    var parentItem: Item? = null,
    val subItems: List<Item>? = emptyList(),
    var type: String? = null,
    var modelNo: String? = null,
    var unit: String? = null,
    var hsCodeEu: String? = null,
    override var name: String = "",
    override val id: Long? = null,
    override val creationTime: Long? = null,
    override val modificationTime: Long? = null,
    override val createdBy: String? = null,
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null,
): BaseNamedEntity() {
    fun getSubtitle() = (if (unit.isNullOrEmpty()) "Unit: Unknown" else "Unit: $unit ") +
            (if (type.isNullOrEmpty()) "" else "Model No.: $type") +
            (if (modelNo.isNullOrEmpty()) "" else "Type: $modelNo") +
            if (hsCodeEu.isNullOrEmpty()) "" else "EU HSCode: $hsCodeEu"
}