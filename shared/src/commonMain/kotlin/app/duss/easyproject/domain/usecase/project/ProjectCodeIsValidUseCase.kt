package app.duss.easyproject.domain.usecase.project

import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class ProjectCodeIsValidUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<String, Boolean>() {
    override suspend fun execute(param: String): Result<Boolean> =
        projectRepository.isCodeAvailable(param)

}