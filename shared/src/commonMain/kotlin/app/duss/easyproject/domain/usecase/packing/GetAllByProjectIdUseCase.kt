package app.duss.easyproject.domain.usecase.packing

import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.domain.repository.PackingRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllByProjectIdUseCase(
    private val repository: PackingRepository,
): BaseCoroutinesUseCaseMultiParam<Long, Int, List<Packing>>() {

    override suspend fun execute(param1: Long, param2: Int): Result<List<Packing>> =
        repository.getAllByProjectId(param1, param2)

}