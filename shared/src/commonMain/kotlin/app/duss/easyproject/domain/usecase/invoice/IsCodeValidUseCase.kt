package app.duss.easyproject.domain.usecase.invoice

import app.duss.easyproject.domain.repository.InvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class IsCodeValidUseCase(
    private val repository: InvoiceRepository,
): BaseCoroutinesUseCase<String, Boolean>() {
    override suspend fun execute(param: String): Result<Boolean> =
        repository.validateCode(param)

}