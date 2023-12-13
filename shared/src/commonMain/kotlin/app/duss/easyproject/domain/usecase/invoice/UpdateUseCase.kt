package app.duss.easyproject.domain.usecase.invoice

import app.duss.easyproject.domain.entity.Invoice
import app.duss.easyproject.domain.params.InvoiceRequest
import app.duss.easyproject.domain.repository.InvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class UpdateUseCase(
    private val repository: InvoiceRepository,
): BaseCoroutinesUseCase<InvoiceRequest, Invoice>() {
    override suspend fun execute(param: InvoiceRequest): Result<Invoice> =
        repository.update(param)

}