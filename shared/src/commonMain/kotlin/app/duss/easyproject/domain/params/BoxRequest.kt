package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class BoxRequest(
    val id: Long? = null,
    var code: String?,
    var weightGross: Double?,
    var length: Double?,
    var width: Double?,
    var height: Double?,
    var boxItems: List<BoxItemRequest>,
)