package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class DeleteUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCase<Long, Boolean>() {
    override suspend fun execute(param: Long): Result<Boolean> =
        repository.delete(param)

}