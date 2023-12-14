package app.duss.easyproject.domain.usecase.item

import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.domain.repository.ItemRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllUseCase(
    private val repository: ItemRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<Item>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<Item>> =
        repository.getAll(param1, param2)

}