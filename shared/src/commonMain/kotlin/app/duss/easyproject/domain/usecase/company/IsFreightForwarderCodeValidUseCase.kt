package app.duss.easyproject.domain.usecase.company

import app.duss.easyproject.domain.repository.CompanyRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class IsFreightForwarderCodeValidUseCase(
    private val repository: CompanyRepository,
): BaseCoroutinesUseCase<String, Boolean>() {
    override suspend fun execute(param: String): Result<Boolean> =
        repository.validateCode(customerCode = null, freightForwarderCode = param, supplierCode = null)

}