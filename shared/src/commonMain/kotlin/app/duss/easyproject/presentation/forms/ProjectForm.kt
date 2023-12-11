package app.duss.easyproject.presentation.forms

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.toDto

data class ProjectForm(
    override val origin: Project,
    override val updated: Project = origin.copy(),
): BaseForm() {
    override fun toRequestDto() = updated.toDto()
}