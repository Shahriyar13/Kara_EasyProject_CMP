package app.duss.easyproject.domain.usecase.invoice

import app.duss.easyproject.domain.repository.InvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class DeleteUseCase(
    private val repository: InvoiceRepository,
): BaseCoroutinesUseCase<Long, Boolean>() {
    override suspend fun execute(param: Long): Result<Boolean> =
        repository.delete(param)

}