package app.duss.easyproject.domain.usecase.item

import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.domain.repository.ItemRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetNewUseCase(
    private val repository: ItemRepository,
): BaseCoroutinesUseCase<Unit, Item>() {
    override suspend fun execute(param: Unit): Result<Item> =
        repository.getNew()

}