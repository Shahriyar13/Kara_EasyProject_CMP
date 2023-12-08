package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
class SupplierCompanyCreateRequest (
    val code: String,
    val company: CompanyCreateRequest,
)