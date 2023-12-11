package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCase<Long, CustomerEnquiry>() {
    override suspend fun execute(param: Long): Result<CustomerEnquiry> =
        repository.getById(param)

}