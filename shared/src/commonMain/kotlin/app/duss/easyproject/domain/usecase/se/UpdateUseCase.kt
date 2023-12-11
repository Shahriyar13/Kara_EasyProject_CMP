package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.params.CustomerEnquiryRequest
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class UpdateUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCase<CustomerEnquiryRequest, CustomerEnquiry>() {
    override suspend fun execute(param: CustomerEnquiryRequest): Result<CustomerEnquiry> =
        repository.update(param)

}