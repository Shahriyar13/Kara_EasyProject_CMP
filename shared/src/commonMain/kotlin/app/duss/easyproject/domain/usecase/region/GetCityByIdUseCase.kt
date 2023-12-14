package app.duss.easyproject.domain.usecase.region

import app.duss.easyproject.domain.entity.RegionCity
import app.duss.easyproject.domain.repository.RegionRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetCityByIdUseCase(
    private val repository: RegionRepository,
): BaseCoroutinesUseCase<Long, RegionCity>() {
    override suspend fun execute(param: Long): Result<RegionCity> =
        repository.getCityById(param)

}