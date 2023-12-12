package app.duss.easyproject.data.repository

import app.duss.easyproject.data.network.client.SupplierEnquiryClient
import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.params.SupplierEnquiryRequest
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SupplierEnquiryRepositoryImpl : SupplierEnquiryRepository, KoinComponent {

    private val client by inject<SupplierEnquiryClient>()

    override suspend fun getAll(query: String?, page: Int): Result<List<SupplierEnquiry>> {
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
    ): Result<List<SupplierEnquiry>> {
        return try {
            val response = client.getAllByProjectId(projectId = projectId, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun validateCode(code: String): Result<Boolean> {
        return try {
            val response = client.validateCode(code = code)
            Result.success(response.data ?: false)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getById(id: Long): Result<SupplierEnquiry> {
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

    override suspend fun create(param: SupplierEnquiryRequest): Result<SupplierEnquiry> {
        return try {
            val response = client.create(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Creating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(param: SupplierEnquiryRequest): Result<SupplierEnquiry> {
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