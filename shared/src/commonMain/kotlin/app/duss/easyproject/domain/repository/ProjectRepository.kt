package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectRequest

interface ProjectRepository {

    suspend fun getAll(page: Int): Result<List<Project>>

    suspend fun getById(id: Long): Result<Project>

    suspend fun getNew(): Result<Project>

    suspend fun isCodeAvailable(code: String): Result<Boolean>

    suspend fun create(param: ProjectRequest): Result<Project>

    suspend fun update(param: ProjectRequest): Result<Project>

    suspend fun delete(id: Long): Result<Boolean>

}