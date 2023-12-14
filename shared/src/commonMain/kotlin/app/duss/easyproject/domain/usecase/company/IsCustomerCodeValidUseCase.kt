package app.duss.easyproject.domain.usecase.company

import app.duss.easyproject.domain.repository.CompanyRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class IsCustomerCodeValidUseCase(
    private val repository: CompanyRepository,
): BaseCoroutinesUseCase<String, Boolean>() {
    override suspend fun execute(param: String): Result<Boolean> =
        repository.validateCode(customerCode = param, freightForwarderCode = null, supplierCode = null)

}