package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetNewUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCase<Unit, SupplierEnquiry>() {
    override suspend fun execute(param: Unit): Result<SupplierEnquiry> =
        repository.getNew()

}