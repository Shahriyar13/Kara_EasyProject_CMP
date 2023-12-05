package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectCreateRequest
import app.duss.easyproject.domain.params.ProjectUpdateRequest

interface ProjectRepository {

    suspend fun getProjectList(page: Int): Result<List<Project>>

    suspend fun getProjectById(id: Long?): Result<Project>

    suspend fun isProjectCodeAvailable(code: String): Result<Boolean>

    suspend fun createProject(param: ProjectCreateRequest): Result<Project?>

    suspend fun updateProject(param: ProjectUpdateRequest): Result<Project?>

    suspend fun deleteProject(id: Long): Result<Boolean>

}