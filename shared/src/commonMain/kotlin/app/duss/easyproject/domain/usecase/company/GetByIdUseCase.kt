package app.duss.easyproject.domain.usecase.company

import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.domain.repository.CompanyRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: CompanyRepository,
): BaseCoroutinesUseCase<Long, Company>() {
    override suspend fun execute(param: Long): Result<Company> =
        repository.getById(param)

}