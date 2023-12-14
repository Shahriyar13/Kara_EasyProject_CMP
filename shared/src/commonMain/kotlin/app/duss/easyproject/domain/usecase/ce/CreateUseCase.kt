package app.duss.easyproject.domain.usecase.ce

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.params.CustomerEnquiryRequest
import app.duss.easyproject.domain.repository.CustomerEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class CreateUseCase(
    private val repository: CustomerEnquiryRepository,
): BaseCoroutinesUseCase<CustomerEnquiryRequest, CustomerEnquiry>() {
    override suspend fun execute(param: CustomerEnquiryRequest): Result<CustomerEnquiry> =
        repository.create(param)

}