package app.duss.easyproject.domain.usecase.project

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectRequest
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class ProjectCreateUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<ProjectRequest, Project>() {
    override suspend fun execute(param: ProjectRequest): Result<Project> =
        projectRepository.create(param)

}