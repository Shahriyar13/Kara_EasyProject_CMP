package app.duss.easyproject.domain.usecase.ce

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.repository.CustomerEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: CustomerEnquiryRepository,
): BaseCoroutinesUseCase<Long, CustomerEnquiry>() {
    override suspend fun execute(param: Long): Result<CustomerEnquiry> =
        repository.getById(param)

}