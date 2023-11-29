package app.duss.easyproject.domain.params

class SupplierCreateRequest (
    val code: String,
    val companyId: Long?,
    val company: CompanyCreateRequest?,
)