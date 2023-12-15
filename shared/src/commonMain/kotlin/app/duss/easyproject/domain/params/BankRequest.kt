package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Bank
import kotlinx.serialization.Serializable

@Serializable
data class BankRequest(
    val id: Long? = null,
    val name: String?,
    val iban: String,
    val bic: String?,
)

fun Bank.toDto() = BankRequest(
    id = id,
    name = name,
    iban = iban,
    bic = bic,
)