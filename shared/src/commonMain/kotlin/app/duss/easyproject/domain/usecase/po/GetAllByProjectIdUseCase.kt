package app.duss.easyproject.domain.usecase.po

import app.duss.easyproject.domain.entity.PurchaseOrder
import app.duss.easyproject.domain.repository.PurchaseOrderRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllByProjectIdUseCase(
    private val repository: PurchaseOrderRepository,
): BaseCoroutinesUseCaseMultiParam<Long, Int, List<PurchaseOrder>>() {

    override suspend fun execute(param1: Long, param2: Int): Result<List<PurchaseOrder>> =
        repository.getAllByProjectId(param1, param2)

}