package app.duss.easyproject.domain.usecase.invoice

import app.duss.easyproject.domain.entity.Invoice
import app.duss.easyproject.domain.repository.InvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllUseCase(
    private val repository: InvoiceRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<Invoice>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<Invoice>> =
        repository.getAll(param1, param2)

}