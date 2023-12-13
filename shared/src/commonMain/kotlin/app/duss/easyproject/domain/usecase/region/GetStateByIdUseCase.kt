package app.duss.easyproject.domain.usecase.region

import app.duss.easyproject.domain.entity.RegionState
import app.duss.easyproject.domain.repository.RegionRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetStateByIdUseCase(
    private val repository: RegionRepository,
): BaseCoroutinesUseCase<Long, RegionState>() {
    override suspend fun execute(param: Long): Result<RegionState> =
        repository.getStateById(param)

}