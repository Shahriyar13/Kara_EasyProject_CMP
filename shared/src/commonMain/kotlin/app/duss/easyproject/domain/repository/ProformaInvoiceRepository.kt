package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.params.PersonRequest

interface ProformaInvoiceRepository {

    suspend fun getAll(page: Int): Result<List<ProformaInvoice>>

    suspend fun getById(id: Long): Result<ProformaInvoice>

    suspend fun getNew(): Result<ProformaInvoice>

    suspend fun validate(param: ProformaInvoiceRequest): Result<Boolean>

    suspend fun create(param: ProformaInvoiceRequest): Result<ProformaInvoice>

    suspend fun update(param: ProformaInvoiceRequest): Result<ProformaInvoice>

    suspend fun delete(id: Long): Result<Boolean>

}