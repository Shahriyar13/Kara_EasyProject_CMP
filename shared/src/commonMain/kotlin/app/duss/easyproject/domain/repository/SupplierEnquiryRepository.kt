package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.params.SupplierEnquiryRequest

interface SupplierEnquiryRepository {

    suspend fun getAll(query: String?, page: Int): Result<List<SupplierEnquiry>>

    suspend fun getAllByProjectId(projectId: Long, page: Int): Result<List<SupplierEnquiry>>

    suspend fun getById(id: Long): Result<SupplierEnquiry>

    suspend fun validateCode(code: String): Result<Boolean>

    suspend fun create(param: SupplierEnquiryRequest): Result<SupplierEnquiry>

    suspend fun update(param: SupplierEnquiryRequest): Result<SupplierEnquiry>

    suspend fun delete(id: Long): Result<Boolean>

}