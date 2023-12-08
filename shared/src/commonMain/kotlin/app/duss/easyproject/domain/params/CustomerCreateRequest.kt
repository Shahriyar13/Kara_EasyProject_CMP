package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
class CustomerCreateRequest (
    val code: String,
    val companyId: Long?,
    val company: CompanyCreateRequest?,
)