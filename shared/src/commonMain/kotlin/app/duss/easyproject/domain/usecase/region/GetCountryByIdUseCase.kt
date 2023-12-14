package app.duss.easyproject.domain.usecase.region

import app.duss.easyproject.domain.entity.RegionCountry
import app.duss.easyproject.domain.repository.RegionRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetCountryByIdUseCase(
    private val repository: RegionRepository,
): BaseCoroutinesUseCase<Long, RegionCountry>() {
    override suspend fun execute(param: Long): Result<RegionCountry> =
        repository.getCountryById(param)

}