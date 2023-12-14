package app.duss.easyproject.domain.usecase.pi

import app.duss.easyproject.domain.entity.ProformaInvoice
import app.duss.easyproject.domain.repository.ProformaInvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: ProformaInvoiceRepository,
): BaseCoroutinesUseCase<Long, ProformaInvoice>() {
    override suspend fun execute(param: Long): Result<ProformaInvoice> =
        repository.getById(param)

}