package app.duss.easyproject.domain.usecase.pi

import app.duss.easyproject.domain.repository.ProformaInvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class IsCodeValidUseCase(
    private val repository: ProformaInvoiceRepository,
): BaseCoroutinesUseCase<String, Boolean>() {
    override suspend fun execute(param: String): Result<Boolean> =
        repository.validateCode(param)

}