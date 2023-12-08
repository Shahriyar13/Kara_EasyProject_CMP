package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
class CompanyUpdateRequest (
    val id: Long,
    val name: String = "",
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
    val contactPersonsIds: List<Long>? = ArrayList(),
    val contactPersons: List<PersonCreateRequest>? = ArrayList(),
    val managingDirectorPersonId: Long? = null,
    val managingDirectorPerson: PersonCreateRequest? = null,
    val banksIds: List<Long>? = ArrayList(),
    val banks: List<BankCreateRequest>? = ArrayList(),
)
