package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class BankRequest(
    val id: Long? = null,
    val title: String?,
    val iban: String,
    val bic: String?,
    val companyId: Long,
)
