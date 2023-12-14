package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.domain.params.PackingRequest

interface PackingRepository {

    suspend fun getAll(query: String?, page: Int): Result<List<Packing>>
    suspend fun getAllByProjectId(projectId: Long, page: Int): Result<List<Packing>>

    suspend fun getById(id: Long): Result<Packing>

    suspend fun validateCode(code: String): Result<Boolean>

    suspend fun create(param: PackingRequest): Result<Packing>

    suspend fun update(param: PackingRequest): Result<Packing>

    suspend fun delete(id: Long): Result<Boolean>

}