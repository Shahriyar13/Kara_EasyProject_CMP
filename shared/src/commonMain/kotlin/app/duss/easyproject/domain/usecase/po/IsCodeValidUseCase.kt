package app.duss.easyproject.domain.usecase.po

import app.duss.easyproject.domain.repository.PurchaseOrderRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class IsCodeValidUseCase(
    private val repository: PurchaseOrderRepository,
): BaseCoroutinesUseCase<String, Boolean>() {
    override suspend fun execute(param: String): Result<Boolean> =
        repository.validateCode(param)

}