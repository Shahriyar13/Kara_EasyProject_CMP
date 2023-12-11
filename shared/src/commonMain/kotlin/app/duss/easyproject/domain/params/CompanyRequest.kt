package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
class CompanyRequest (
    val id: Long? = null,
    val name: String = "",
    val supplierCode: String?,
    val customerCode: String?,
    val street: String? = null,
    val postcode: String? = null,
    val cityId: Long? = null,
    val taxNumber: String? = null,
    val vatNumber: String? = null,
    val handelsregisternumber: String? = null,
    val telephone: String? = null,
    val fax: String? = null,
    val email: String? = null,
    val website: String? = null,
    val contactPersons: List<PersonRequest>? = ArrayList(),
    val managingDirectorPerson: PersonRequest? = null,
    val banks: List<BankRequest>? = ArrayList(),
)
