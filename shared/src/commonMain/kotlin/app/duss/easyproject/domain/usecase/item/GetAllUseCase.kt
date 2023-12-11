package app.duss.easyproject.domain.usecase.se

import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetAllUseCase(
    private val repository: SupplierEnquiryRepository,
): BaseCoroutinesUseCase<Int, List<SupplierEnquiry>>() {

    override suspend fun execute(param: Int): Result<List<SupplierEnquiry>> =
        repository.getAll(param)

}