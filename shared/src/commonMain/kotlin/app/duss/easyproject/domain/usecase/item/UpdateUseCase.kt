package app.duss.easyproject.domain.usecase.item

import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.domain.params.ItemRequest
import app.duss.easyproject.domain.repository.ItemRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class UpdateUseCase(
    private val repository: ItemRepository,
): BaseCoroutinesUseCase<ItemRequest, Item>() {
    override suspend fun execute(param: ItemRequest): Result<Item> =
        repository.update(param)

}