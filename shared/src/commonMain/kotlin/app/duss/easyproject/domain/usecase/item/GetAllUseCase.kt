package app.duss.easyproject.domain.usecase.item

import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.domain.repository.ItemRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetAllUseCase(
    private val repository: ItemRepository,
): BaseCoroutinesUseCase<Int, List<Item>>() {

    override suspend fun execute(param: Int): Result<List<Item>> =
        repository.getAll(param)

}