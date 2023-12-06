package app.duss.easyproject.domain.usecase.project

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class ProjectGetNewUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<Unit, Project>() {
    override suspend fun execute(param: Unit): Result<Project> =
        projectRepository.getNew()

}