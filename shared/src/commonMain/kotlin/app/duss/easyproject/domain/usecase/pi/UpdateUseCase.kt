package app.duss.easyproject.domain.usecase.pi

import app.duss.easyproject.domain.entity.ProformaInvoice
import app.duss.easyproject.domain.params.ProformaInvoiceRequest
import app.duss.easyproject.domain.repository.ProformaInvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class UpdateUseCase(
    private val repository: ProformaInvoiceRepository,
): BaseCoroutinesUseCase<ProformaInvoiceRequest, ProformaInvoice>() {
    override suspend fun execute(param: ProformaInvoiceRequest): Result<ProformaInvoice> =
        repository.update(param)

}