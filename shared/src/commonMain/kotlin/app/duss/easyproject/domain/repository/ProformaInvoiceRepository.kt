package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.ProformaInvoice
import app.duss.easyproject.domain.params.ProformaInvoiceRequest

interface ProformaInvoiceRepository {

    suspend fun getAll(query: String?, page: Int): Result<List<ProformaInvoice>>

    suspend fun getAllByProjectId(projectId: Long, page: Int): Result<List<ProformaInvoice>>

    suspend fun getById(id: Long): Result<ProformaInvoice>

    suspend fun validateCode(code: String): Result<Boolean>

    suspend fun create(param: ProformaInvoiceRequest): Result<ProformaInvoice>

    suspend fun update(param: ProformaInvoiceRequest): Result<ProformaInvoice>

    suspend fun delete(id: Long): Result<Boolean>

}