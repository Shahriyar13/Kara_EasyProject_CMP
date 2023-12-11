package app.duss.easyproject.domain.usecase.ce

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.repository.CustomerEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetAllUseCase(
    private val repository: CustomerEnquiryRepository,
): BaseCoroutinesUseCase<Int, List<CustomerEnquiry>>() {

    override suspend fun execute(param: Int): Result<List<CustomerEnquiry>> =
        repository.getAll(param)

}