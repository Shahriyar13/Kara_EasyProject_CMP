package app.duss.easyproject.domain.usecase.packing

import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.domain.repository.PackingRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllUseCase(
    private val repository: PackingRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<Packing>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<Packing>> =
        repository.getAll(param1, param2)

}