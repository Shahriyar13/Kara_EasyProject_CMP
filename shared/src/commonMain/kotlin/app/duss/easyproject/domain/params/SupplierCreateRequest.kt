package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
class SupplierCreateRequest (
    val code: String,
    val companyId: Long?,
    val company: CompanyCreateRequest?,
)