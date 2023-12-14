package app.duss.easyproject.domain.usecase.quotation

import app.duss.easyproject.domain.entity.Quotation
import app.duss.easyproject.domain.repository.QuotationRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllByProjectIdUseCase(
    private val repository: QuotationRepository,
): BaseCoroutinesUseCaseMultiParam<Long, Int, List<Quotation>>() {

    override suspend fun execute(param1: Long, param2: Int): Result<List<Quotation>> =
        repository.getAllByProjectId(param1, param2)

}