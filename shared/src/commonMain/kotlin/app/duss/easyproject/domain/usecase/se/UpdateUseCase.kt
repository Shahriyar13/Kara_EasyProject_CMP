package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.params.SupplierEnquiryRequest
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class UpdateUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCase<SupplierEnquiryRequest, SupplierEnquiry>() {
    override suspend fun execute(param: SupplierEnquiryRequest): Result<SupplierEnquiry> =
        repository.update(param)

}