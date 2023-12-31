package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class CountryCustomPort(
    override val name: String,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?
): BaseNamedEntity()