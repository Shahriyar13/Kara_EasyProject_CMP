package app.duss.easyproject.domain.usecase.packing

import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.domain.repository.PackingRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: PackingRepository,
): BaseCoroutinesUseCase<Long, Packing>() {
    override suspend fun execute(param: Long): Result<Packing> =
        repository.getById(param)

}