package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val symbol: String?,
    var street: String?,
    var postcode: String?,
    val city: City?,
    var taxNumber: String?,
    var vatNumber: String?,
    var handelsregisternumber: String?,
    var telephone: String?,
    var fax: String?,
    var email: String?,
    var website: String?,
    val contactPersons: List<Person>?,
    val managingDirectorPerson: Person?,
    val banks: List<Bank>?,
    override val name: String,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?
): BaseNamedEntity()