package app.duss.easyproject.domain.usecase.project

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectCreateRequest
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class ProjectCreateUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<ProjectCreateRequest, Project>() {
    override suspend fun execute(param: ProjectCreateRequest): Result<Project> =
        projectRepository.createProject(param)

}