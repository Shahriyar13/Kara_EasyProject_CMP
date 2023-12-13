package app.duss.easyproject.domain.usecase.po

import app.duss.easyproject.domain.entity.PurchaseOrder
import app.duss.easyproject.domain.repository.PurchaseOrderRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: PurchaseOrderRepository,
): BaseCoroutinesUseCase<Long, PurchaseOrder>() {
    override suspend fun execute(param: Long): Result<PurchaseOrder> =
        repository.getById(param)

}