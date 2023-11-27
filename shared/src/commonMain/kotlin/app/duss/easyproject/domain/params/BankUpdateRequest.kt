package app.duss.easyproject.domain.params

data class BankUpdateRequest(
    val id: Long,
    val title: String?,
    val iban: String,
    val bic: String?,
    val companyId: Long,
)
