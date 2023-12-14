package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<SupplierEnquiry>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<SupplierEnquiry>> =
        repository.getAll(param1, param2)

}