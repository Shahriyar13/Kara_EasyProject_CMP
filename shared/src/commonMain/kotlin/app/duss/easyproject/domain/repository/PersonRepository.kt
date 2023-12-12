package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.params.PersonRequest

interface PersonRepository {

    suspend fun getAll(query: String?, page: Int): Result<List<Person>>

    suspend fun getAllByCompanyId(companyId: Long, page: Int): Result<List<Person>>

    suspend fun getById(id: Long): Result<Person>

    suspend fun create(param: PersonRequest): Result<Person>

    suspend fun update(param: PersonRequest): Result<Person>

    suspend fun delete(id: Long): Result<Boolean>

}