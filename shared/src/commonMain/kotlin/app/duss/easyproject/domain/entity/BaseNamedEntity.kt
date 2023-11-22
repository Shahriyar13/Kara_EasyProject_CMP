package app.duss.easyproject.domain.entity

sealed class BaseNamedEntity: BaseEntity() {
    abstract val name: String
}