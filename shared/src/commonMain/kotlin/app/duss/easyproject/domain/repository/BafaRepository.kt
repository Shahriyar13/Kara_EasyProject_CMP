package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.params.PersonRequest

interface BafaRepository {

    suspend fun getAll(page: Int): Result<List<Bafa>>

    suspend fun getById(id: Long): Result<Bafa>

    suspend fun getNew(): Result<Bafa>

    suspend fun validate(param: BafaRequest): Result<Boolean>

    suspend fun create(param: BafaRequest): Result<Bafa>

    suspend fun update(param: BafaRequest): Result<Bafa>

    suspend fun delete(id: Long): Result<Boolean>

}