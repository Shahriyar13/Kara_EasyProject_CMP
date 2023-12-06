package app.duss.easyproject.domain.usecase.project

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class ProjectsGetAllUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<Int, List<Project>>() {

    override suspend fun execute(param: Int): Result<List<Project>> =
        projectRepository.getAll(param)

}