package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.params.CustomerEnquiryRequest

interface CustomerEnquiryRepository {

    suspend fun getAll(page: Int): Result<List<CustomerEnquiry>>

    suspend fun getById(id: Long): Result<CustomerEnquiry>

    suspend fun getNew(): Result<CustomerEnquiry>

    suspend fun isCodeAvailable(code: String): Result<Boolean>

    suspend fun create(param: CustomerEnquiryRequest): Result<CustomerEnquiry>

    suspend fun update(param: CustomerEnquiryRequest): Result<CustomerEnquiry>

    suspend fun delete(id: Long): Result<Boolean>

}