package app.duss.easyproject.domain.usecase.item

import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.params.SupplierEnquiryRequest
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class CreateUseCase(
    private val repository: ItemRepository,
): BaseCoroutinesUseCase<SupplierEnquiryRequest, SupplierEnquiry>() {
    override suspend fun execute(param: SupplierEnquiryRequest): Result<SupplierEnquiry> =
        repository.create(param)

}