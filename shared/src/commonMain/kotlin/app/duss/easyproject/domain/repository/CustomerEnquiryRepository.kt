package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.params.CustomerEnquiryRequest

interface CustomerEnquiryRepository {

    suspend fun getAll(query: String?, page: Int): Result<List<CustomerEnquiry>>

    suspend fun getAllByProjectId(projectId: Long, page: Int): Result<List<CustomerEnquiry>>

    suspend fun getById(id: Long): Result<CustomerEnquiry>

    suspend fun create(param: CustomerEnquiryRequest): Result<CustomerEnquiry>

    suspend fun update(param: CustomerEnquiryRequest): Result<CustomerEnquiry>

    suspend fun delete(id: Long): Result<Boolean>

}