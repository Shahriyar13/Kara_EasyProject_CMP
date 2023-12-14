package app.duss.easyproject.domain.usecase.project

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class ProjectsGetAllUseCase(
    private val projectRepository: ProjectRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<Project>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<Project>> =
        projectRepository.getAll(param1, param2)

}