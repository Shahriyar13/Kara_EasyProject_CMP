package app.duss.easyproject.domain.usecase.packing

import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.domain.params.PackingRequest
import app.duss.easyproject.domain.repository.PackingRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class CreateUseCase(
    private val repository: PackingRepository,
): BaseCoroutinesUseCase<PackingRequest, Packing>() {
    override suspend fun execute(param: PackingRequest): Result<Packing> =
        repository.create(param)

}