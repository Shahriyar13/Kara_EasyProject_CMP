package app.duss.easyproject.data.repository

import app.duss.easyproject.data.network.client.CompanyClient
import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.domain.params.CompanyRequest
import app.duss.easyproject.domain.repository.CompanyRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CompanyRepositoryImpl : CompanyRepository, KoinComponent {

    private val client by inject<CompanyClient>()

    override suspend fun getAll(query: String?, page: Int): Result<List<Company>> {
        return try {
            val response = client.getAll(query = query, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
    override suspend fun getAllCustomers(query: String?, page: Int): Result<List<Company>> {
        return try {
            val response = client.getAllCustomers(query = query, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
    override suspend fun getAllSuppliers(query: String?, page: Int): Result<List<Company>> {
        return try {
            val response = client.getAllSuppliers(query = query, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
    override suspend fun getAllFreightForwarders(query: String?, page: Int): Result<List<Company>> {
        return try {
            val response = client.getAllFreightForwarders(query = query, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun validateCode(
        customerCode: String?,
        supplierCode: String?,
        freightForwarderCode: String?
    ): Result<Boolean> {
        return try {
            val response = client.validateCode(
                customerCode = customerCode,
                supplierCode = supplierCode,
                freightForwarderCode = freightForwarderCode,
            )
            Result.success(response.data ?: false)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getById(id: Long): Result<Company> {
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

    override suspend fun create(param: CompanyRequest): Result<Company> {
        return try {
            val response = client.create(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Creating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(param: CompanyRequest): Result<Company> {
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