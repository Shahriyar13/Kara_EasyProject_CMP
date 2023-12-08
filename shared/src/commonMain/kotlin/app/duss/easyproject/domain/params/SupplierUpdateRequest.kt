package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
class SupplierUpdateRequest (
    val id: Long,
    val code: String,
    val companyId: Long,
)