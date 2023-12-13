package app.duss.easyproject.domain.usecase.ce

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.repository.CustomerEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllByProjectIdUseCase(
    private val repository: CustomerEnquiryRepository,
): BaseCoroutinesUseCaseMultiParam<Long, Int, List<CustomerEnquiry>>() {

    override suspend fun execute(param1: Long, param2: Int): Result<List<CustomerEnquiry>> =
        repository.getAllByProjectId(param1, param2)

}