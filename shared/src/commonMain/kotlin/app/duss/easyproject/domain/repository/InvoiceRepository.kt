package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Invoice
import app.duss.easyproject.domain.params.InvoiceRequest

interface InvoiceRepository {

    suspend fun getAll(query: String?, page: Int): Result<List<Invoice>>

    suspend fun getAllByProjectId(projectId: Long, page: Int): Result<List<Invoice>>

    suspend fun getById(id: Long): Result<Invoice>

    suspend fun validateCode(code: String): Result<Boolean>

    suspend fun create(param: InvoiceRequest): Result<Invoice>

    suspend fun update(param: InvoiceRequest): Result<Invoice>

    suspend fun delete(id: Long): Result<Boolean>

}