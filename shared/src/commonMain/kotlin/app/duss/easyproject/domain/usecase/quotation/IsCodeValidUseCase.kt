package app.duss.easyproject.domain.usecase.quotation

import app.duss.easyproject.domain.repository.QuotationRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class IsCodeValidUseCase(
    private val repository: QuotationRepository,
): BaseCoroutinesUseCase<String, Boolean>() {
    override suspend fun execute(param: String): Result<Boolean> =
        repository.validateCode(param)

}