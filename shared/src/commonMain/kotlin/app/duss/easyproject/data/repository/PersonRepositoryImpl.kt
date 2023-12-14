package app.duss.easyproject.data.repository

import app.duss.easyproject.data.network.client.PersonClient
import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.params.PersonRequest
import app.duss.easyproject.domain.repository.PersonRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PersonRepositoryImpl : PersonRepository, KoinComponent {

    private val client by inject<PersonClient>()

    override suspend fun getAll(query: String?, page: Int): Result<List<Person>> {
        return try {
            val response = client.getAll(query = query, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getAllByCompanyId(
        companyId: Long,
        page: Int
    ): Result<List<Person>> {
        return try {
            val response = client.getAllByCompanyId(companyId = companyId, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
    override suspend fun getById(id: Long): Result<Person> {
        return try {
            val response = client.getById(id = id)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun create(param: PersonRequest): Result<Person> {
        return try {
            val response = client.create(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Creating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(param: PersonRequest): Result<Person> {
        return try {
            val response = client.update(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Updating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(id: Long): Result<Boolean> {
        return try {
            val response = client.deleteById(id = id)
            Result.success(response.data ?: false)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}