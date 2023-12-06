package app.duss.easyproject.domain.usecase.project

import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class ProjectDeleteUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<Long, Boolean>() {
    override suspend fun execute(param: Long): Result<Boolean> =
        projectRepository.deleteProject(param)

}