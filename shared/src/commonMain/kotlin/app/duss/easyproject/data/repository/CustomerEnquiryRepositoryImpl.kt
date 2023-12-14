package app.duss.easyproject.data.repository

import app.duss.easyproject.data.network.client.CustomerEnquiryClient
import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.params.CustomerEnquiryRequest
import app.duss.easyproject.domain.repository.CustomerEnquiryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CustomerEnquiryRepositoryImpl : CustomerEnquiryRepository, KoinComponent {

    private val client by inject<CustomerEnquiryClient>()

    override suspend fun getAll(query: String?, page: Int): Result<List<CustomerEnquiry>> {
        return try {
            val response = client.getAll(query = query, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getAllByProjectId(
        projectId: Long,
        page: Int
    ): Result<List<CustomerEnquiry>> {
        return try {
            val response = client.getAllByProjectId(projectId = projectId, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getById(id: Long): Result<CustomerEnquiry> {
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

    override suspend fun create(param: CustomerEnquiryRequest): Result<CustomerEnquiry> {
        return try {
            val response = client.create(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Creating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(param: CustomerEnquiryRequest): Result<CustomerEnquiry> {
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