package app.duss.easyproject.domain.usecase.company

import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.domain.params.CompanyRequest
import app.duss.easyproject.domain.repository.CompanyRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class CreateUseCase(
    private val repository: CompanyRepository,
): BaseCoroutinesUseCase<CompanyRequest, Company>() {
    override suspend fun execute(param: CompanyRequest): Result<Company> =
        repository.create(param)

}