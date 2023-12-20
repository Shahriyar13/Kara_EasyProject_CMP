package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
sealed class BaseEntity {
    abstract val id: Long?
    abstract val creationTime: Long?
    abstract val modificationTime: Long?
    abstract val createdBy: String?
    abstract val modifiedBy: String?
    abstract val creatorId: Long?
    abstract val modifierId: Long?
    val uniqueId: Long = Random.nextLong() //TODO change it to uuid
}