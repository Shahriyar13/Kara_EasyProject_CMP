package app.duss.easyproject.domain.usecase.ce

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.repository.CustomerEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetNewUseCase(
    private val repository: CustomerEnquiryRepository,
): BaseCoroutinesUseCase<Unit, CustomerEnquiry>() {
    override suspend fun execute(param: Unit): Result<CustomerEnquiry> =
        repository.getNew()

}