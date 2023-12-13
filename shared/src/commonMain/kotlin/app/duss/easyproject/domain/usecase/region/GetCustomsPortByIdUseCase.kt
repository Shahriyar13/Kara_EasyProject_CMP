package app.duss.easyproject.domain.usecase.region

import app.duss.easyproject.domain.entity.RegionCustomsPort
import app.duss.easyproject.domain.repository.RegionRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetCustomsPortByIdUseCase(
    private val repository: RegionRepository,
): BaseCoroutinesUseCase<Long, RegionCustomsPort>() {
    override suspend fun execute(param: Long): Result<RegionCustomsPort> =
        repository.getCustomsPortById(param)

}