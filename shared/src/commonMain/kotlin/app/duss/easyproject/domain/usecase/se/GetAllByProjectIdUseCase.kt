package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllByProjectIdUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCaseMultiParam<Long, Int, List<SupplierEnquiry>>() {

    override suspend fun execute(param1: Long, param2: Int): Result<List<SupplierEnquiry>> =
        repository.getAllByProjectId(param1, param2)

}