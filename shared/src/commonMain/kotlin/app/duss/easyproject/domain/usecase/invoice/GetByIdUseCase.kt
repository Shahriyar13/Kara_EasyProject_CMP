package app.duss.easyproject.domain.usecase.invoice

import app.duss.easyproject.domain.entity.Invoice
import app.duss.easyproject.domain.repository.InvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: InvoiceRepository,
): BaseCoroutinesUseCase<Long, Invoice>() {
    override suspend fun execute(param: Long): Result<Invoice> =
        repository.getById(param)

}