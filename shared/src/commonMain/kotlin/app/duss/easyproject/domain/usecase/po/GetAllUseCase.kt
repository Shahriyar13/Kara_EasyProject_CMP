package app.duss.easyproject.domain.usecase.po

import app.duss.easyproject.domain.entity.PurchaseOrder
import app.duss.easyproject.domain.repository.PurchaseOrderRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllUseCase(
    private val repository: PurchaseOrderRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<PurchaseOrder>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<PurchaseOrder>> =
        repository.getAll(param1, param2)

}