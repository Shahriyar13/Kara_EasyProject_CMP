package app.duss.easyproject.domain.usecase

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository

class ProjectGetByIdUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<Long?, Project>() {
    override suspend fun execute(param: Long?): Result<Project> =
        projectRepository.getProjectById(param)

}