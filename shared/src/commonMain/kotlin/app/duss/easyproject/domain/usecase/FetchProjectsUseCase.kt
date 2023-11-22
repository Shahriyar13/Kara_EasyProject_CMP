package app.duss.easyproject.domain.usecase

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository

class FetchProjectsUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCase<Long, List<Project>>() {

    override suspend fun execute(page: Long): Result<List<Project>> =
        projectRepository.getProjectList(page)

}