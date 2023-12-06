package app.duss.easyproject.domain.usecase.project

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectUpdateRequest
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class ProjectUpdateUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<ProjectUpdateRequest, Project>() {
    override suspend fun execute(param: ProjectUpdateRequest): Result<Project> =
        projectRepository.update(param)

}