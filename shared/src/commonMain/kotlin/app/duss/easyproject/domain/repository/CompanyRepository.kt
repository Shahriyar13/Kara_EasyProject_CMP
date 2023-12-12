package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.domain.params.CompanyRequest

interface CompanyRepository {

    suspend fun getAll(query: String?, page: Int): Result<List<Company>>
    suspend fun getAllCustomers(query: String?, page: Int): Result<List<Company>>
    suspend fun getAllSuppliers(query: String?, page: Int): Result<List<Company>>
    suspend fun getAllFreightForwarders(query: String?, page: Int): Result<List<Company>>

    suspend fun getById(id: Long): Result<Company>

    suspend fun validateCode(customerCode: String?, supplierCode: String?, freightForwarderCode: String?): Result<Boolean>

    suspend fun create(param: CompanyRequest): Result<Company>

    suspend fun update(param: CompanyRequest): Result<Company>

    suspend fun delete(id: Long): Result<Boolean>

}