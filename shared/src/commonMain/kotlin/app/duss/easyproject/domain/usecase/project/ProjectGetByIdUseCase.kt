package app.duss.easyproject.domain.usecase.project

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class ProjectGetByIdUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<Long?, Project>() {
    override suspend fun execute(param: Long?): Result<Project> =
        projectRepository.getProjectById(param)

}