package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Company
import kotlinx.serialization.Serializable

@Serializable
class CompanyRequest (
    val id: Long? = null,
    val name: String = "",
    val supplierCode: String?,
    val customerCode: String?,
    val freightForwarderCode: String?,
    val isCustomer: Boolean,
    val isSupplier: Boolean,
    val isFreightForwarder: Boolean,
    val symbol: String? = null,
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


fun Company.toDto(): CompanyRequest = CompanyRequest(
    id = id,
    name = name,
    supplierCode = supplierCode,
    customerCode = customerCode,
    freightForwarderCode = freightForwarderCode,
    symbol = symbol,
    street = street,
    postcode = postcode,
    cityId = city?.id,
    taxNumber = taxNumber,
    vatNumber = vatNumber,
    handelsregisternumber = handelsregisternumber,
    telephone = telephone,
    fax = fax,
    email = email,
    website = website,
    contactPersons = contactPersons?.map {
        it.toDto()
    },
    isCustomer = isCustomer,
    isSupplier = isSupplier,
    isFreightForwarder = isFreightForwarder,
    managingDirectorPerson = managingDirectorPerson?.toDto(),
    banks = banks?.map {
        it.toDto()
    }
)