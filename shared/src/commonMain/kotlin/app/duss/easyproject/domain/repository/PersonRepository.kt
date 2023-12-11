package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.params.PersonRequest

interface PersonRepository {

    suspend fun getAll(page: Int): Result<List<Person>>

    suspend fun getById(id: Long): Result<Person>

    suspend fun getNew(): Result<Person>

    suspend fun validate(param: PersonRequest): Result<Boolean>

    suspend fun create(param: PersonRequest): Result<Person>

    suspend fun update(param: PersonRequest): Result<Person>

    suspend fun delete(id: Long): Result<Boolean>

}