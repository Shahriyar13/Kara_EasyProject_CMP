package app.duss.easyproject.domain.params

class CustomerCreateRequest (
    val code: String,
    val companyId: Long?,
    val company: CompanyCreateRequest?,
)