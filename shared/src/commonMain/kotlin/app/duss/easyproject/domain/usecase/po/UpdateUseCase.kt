package app.duss.easyproject.domain.usecase.po

import app.duss.easyproject.domain.entity.PurchaseOrder
import app.duss.easyproject.domain.params.PurchaseOrderRequest
import app.duss.easyproject.domain.repository.PurchaseOrderRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class UpdateUseCase(
    private val repository: PurchaseOrderRepository,
): BaseCoroutinesUseCase<PurchaseOrderRequest, PurchaseOrder>() {
    override suspend fun execute(param: PurchaseOrderRequest): Result<PurchaseOrder> =
        repository.update(param)

}