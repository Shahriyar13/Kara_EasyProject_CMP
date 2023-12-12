package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Quotation
import app.duss.easyproject.domain.params.QuotationRequest

interface QuotationRepository {

    suspend fun getAll(page: Int): Result<List<Quotation>>

    suspend fun getById(id: Long): Result<Quotation>

    suspend fun getNew(): Result<Quotation>

    suspend fun validate(param: QuotationRequest): Result<Boolean>

    suspend fun create(param: QuotationRequest): Result<Quotation>

    suspend fun update(param: QuotationRequest): Result<Quotation>

    suspend fun delete(id: Long): Result<Boolean>

}