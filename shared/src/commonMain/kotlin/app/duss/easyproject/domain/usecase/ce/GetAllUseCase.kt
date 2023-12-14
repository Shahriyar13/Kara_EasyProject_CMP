package app.duss.easyproject.domain.usecase.ce

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.repository.CustomerEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllUseCase(
    private val repository: CustomerEnquiryRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<CustomerEnquiry>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<CustomerEnquiry>> =
        repository.getAll(param1, param2)

}