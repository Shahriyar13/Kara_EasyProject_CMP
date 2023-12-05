package app.duss.easyproject.domain.usecase

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository

class ProjectsGetAllUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<Int, List<Project>>() {

    override suspend fun execute(param: Int): Result<List<Project>> =
        projectRepository.getProjectList(param)

}