package app.duss.easyproject.domain.usecase.invoice

import app.duss.easyproject.domain.entity.Invoice
import app.duss.easyproject.domain.repository.InvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllByProjectIdUseCase(
    private val repository: InvoiceRepository,
): BaseCoroutinesUseCaseMultiParam<Long, Int, List<Invoice>>() {

    override suspend fun execute(param1: Long, param2: Int): Result<List<Invoice>> =
        repository.getAllByProjectId(param1, param2)

}