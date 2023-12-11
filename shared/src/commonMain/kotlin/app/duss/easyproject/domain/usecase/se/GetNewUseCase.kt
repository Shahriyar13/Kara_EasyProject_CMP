package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetNewUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCase<Unit, CustomerEnquiry>() {
    override suspend fun execute(param: Unit): Result<CustomerEnquiry> =
        repository.getNew()

}