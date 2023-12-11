package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.domain.params.CompanyRequest

interface CompanyRepository {

    suspend fun getAll(page: Int): Result<List<Company>>

    suspend fun getById(id: Long): Result<Company>

    suspend fun getNew(): Result<Company>

    suspend fun validate(param: CompanyRequest): Result<Boolean>

    suspend fun create(param: CompanyRequest): Result<Company>

    suspend fun update(param: CompanyRequest): Result<Company>

    suspend fun delete(id: Long): Result<Boolean>

}