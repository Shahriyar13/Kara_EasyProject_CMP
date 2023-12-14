package app.duss.easyproject.domain.enums

enum class PaymentType(val id: Int) {
    Unknown(0),
    ByBankTT(1),
    ByBankLC(2),
}