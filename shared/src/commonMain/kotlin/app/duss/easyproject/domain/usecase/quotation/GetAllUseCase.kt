package app.duss.easyproject.domain.usecase.quotation

import app.duss.easyproject.domain.entity.Quotation
import app.duss.easyproject.domain.repository.QuotationRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllUseCase(
    private val repository: QuotationRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<Quotation>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<Quotation>> =
        repository.getAll(param1, param2)

}