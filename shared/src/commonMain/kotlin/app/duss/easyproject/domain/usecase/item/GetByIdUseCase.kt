package app.duss.easyproject.domain.usecase.item

import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.domain.repository.ItemRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: ItemRepository,
): BaseCoroutinesUseCase<Long, Item>() {
    override suspend fun execute(param: Long): Result<Item> =
        repository.getById(param)

}