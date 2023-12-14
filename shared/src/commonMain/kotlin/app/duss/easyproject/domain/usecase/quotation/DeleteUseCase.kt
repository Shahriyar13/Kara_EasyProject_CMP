package app.duss.easyproject.domain.usecase.quotation

import app.duss.easyproject.domain.repository.QuotationRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class DeleteUseCase(
    private val repository: QuotationRepository,
): BaseCoroutinesUseCase<Long, Boolean>() {
    override suspend fun execute(param: Long): Result<Boolean> =
        repository.delete(param)

}