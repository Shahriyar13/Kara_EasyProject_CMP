package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
sealed class BaseDocumentEntity: BaseEntity() {
    abstract val code: String
    abstract val codeExtension: String?
    abstract val annualId: Int
    abstract val time: Long
    abstract val sendTime: Long?
}