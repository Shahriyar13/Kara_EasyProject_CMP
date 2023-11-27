package app.duss.easyproject.domain.params

data class BankCreateRequest(
    val title: String?,
    val iban: String,
    val bic: String?,
    val companyId: Long,
)
