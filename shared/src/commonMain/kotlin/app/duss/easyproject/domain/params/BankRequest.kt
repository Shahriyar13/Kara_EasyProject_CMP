package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class BankRequest(
    val id: Long? = null,
    val name: String?,
    val iban: String,
    val bic: String?,
)
