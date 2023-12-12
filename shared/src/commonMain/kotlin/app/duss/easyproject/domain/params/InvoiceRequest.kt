package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceRequest (
    val id: Long? = null,
    val code: String?,
    val codeExtension: String?,
    val annualId: Int?,
    val projectId: Long?,
    val time: Long,
    val sendTime: Long?,
    val packingId: Long,
)