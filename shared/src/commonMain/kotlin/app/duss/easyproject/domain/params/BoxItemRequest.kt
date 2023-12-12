package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class BoxItemRequest(
    val id: Long? = null,
    var quotationItemId: Long,
    var quantity: Int,
    val code: String?,
)