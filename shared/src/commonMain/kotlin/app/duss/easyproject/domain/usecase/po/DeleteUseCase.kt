package app.duss.easyproject.domain.usecase.po

import app.duss.easyproject.domain.repository.PurchaseOrderRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class DeleteUseCase(
    private val repository: PurchaseOrderRepository,
): BaseCoroutinesUseCase<Long, Boolean>() {
    override suspend fun execute(param: Long): Result<Boolean> =
        repository.delete(param)

}