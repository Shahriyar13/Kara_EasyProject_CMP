package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Project

interface CompanyRepository {

    suspend fun getProjectList(page: Long): Result<List<Project>>

    suspend fun getProjectById(id: Long): Result<Project>

    suspend fun createProject(param: Project)

    suspend fun updateProject(param: Project)

    suspend fun deleteProject(param: Long)

}