package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.params.PersonRequest

interface InvoiceRepository {

    suspend fun getAll(page: Int): Result<List<Invoice>>

    suspend fun getById(id: Long): Result<Invoice>

    suspend fun getNew(): Result<Invoice>

    suspend fun validate(param: InvoiceRequest): Result<Boolean>

    suspend fun create(param: InvoiceRequest): Result<Invoice>

    suspend fun update(param: InvoiceRequest): Result<Invoice>

    suspend fun delete(id: Long): Result<Boolean>

}