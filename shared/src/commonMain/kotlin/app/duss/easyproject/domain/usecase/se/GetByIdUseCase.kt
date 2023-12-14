package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCase<Long, SupplierEnquiry>() {
    override suspend fun execute(param: Long): Result<SupplierEnquiry> =
        repository.getById(param)

}