package app.duss.easyproject.domain.usecase.pi

import app.duss.easyproject.domain.repository.ProformaInvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class DeleteUseCase(
    private val repository: ProformaInvoiceRepository,
): BaseCoroutinesUseCase<Long, Boolean>() {
    override suspend fun execute(param: Long): Result<Boolean> =
        repository.delete(param)

}