package app.duss.easyproject.domain.usecase.ce

import app.duss.easyproject.domain.repository.CustomerEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class DeleteUseCase(
    private val repository: CustomerEnquiryRepository,
): BaseCoroutinesUseCase<Long, Boolean>() {
    override suspend fun execute(param: Long): Result<Boolean> =
        repository.delete(param)

}