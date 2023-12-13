package app.duss.easyproject.domain.usecase.quotation

import app.duss.easyproject.domain.entity.Quotation
import app.duss.easyproject.domain.repository.QuotationRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: QuotationRepository,
): BaseCoroutinesUseCase<Long, Quotation>() {
    override suspend fun execute(param: Long): Result<Quotation> =
        repository.getById(param)

}