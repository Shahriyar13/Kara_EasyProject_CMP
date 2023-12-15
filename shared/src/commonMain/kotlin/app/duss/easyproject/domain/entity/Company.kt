package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    var symbol: String? = null,
    var customerCode: String? = null,
    var supplierCode: String? = null,
    var freightForwarderCode: String? = null,
    var isCustomer: Boolean = false,
    var isSupplier: Boolean = false,
    var isFreightForwarder: Boolean = false,
    var street: String? = null,
    var postcode: String? = null,
    val city: RegionCity? = null,
    var taxNumber: String? = null,
    var vatNumber: String? = null,
    var handelsregisternumber: String? = null,
    var telephone: String? = null,
    var fax: String? = null,
    var email: String? = null,
    var website: String? = null,
    val contactPersons: List<Person>? = emptyList(),
    val managingDirectorPerson: Person? = null,
    val banks: List<Bank>? = emptyList(),
    override var name: String = "",
    override val id: Long? = null,
    override val creationTime: Long? = null,
    override val modificationTime: Long? = null,
    override val createdBy: String? = null,
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null,
): BaseNamedEntity() {
    fun getAddress(): String = (street?.let { "$it, " } ?: "") +
            (postcode?.let { "$it " } ?: "") +
            (city?.name?.let { "$it, " } ?: "") +
            (city?.state?.name?.let { "$it, " } ?: "") +
            (city?.state?.country?.name ?: "")
}