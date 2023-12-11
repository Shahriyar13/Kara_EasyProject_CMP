package app.duss.easyproject.presentation.forms

import app.duss.easyproject.domain.entity.BaseDocumentEntity

sealed class BaseForm {

    abstract val origin: BaseDocumentEntity
    abstract val updated: BaseDocumentEntity
    fun hasUnsavedChanges(): Boolean = origin != updated

    abstract fun toRequestDto(): Any

}