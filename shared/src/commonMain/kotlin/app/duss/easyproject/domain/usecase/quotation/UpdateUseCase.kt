package app.duss.easyproject.domain.usecase.quotation

import app.duss.easyproject.domain.entity.Quotation
import app.duss.easyproject.domain.params.QuotationRequest
import app.duss.easyproject.domain.repository.QuotationRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class UpdateUseCase(
    private val repository: QuotationRepository,
): BaseCoroutinesUseCase<QuotationRequest, Quotation>() {
    override suspend fun execute(param: QuotationRequest): Result<Quotation> =
        repository.update(param)

}