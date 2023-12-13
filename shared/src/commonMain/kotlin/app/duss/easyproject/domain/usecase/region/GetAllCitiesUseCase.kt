package app.duss.easyproject.domain.usecase.region

import app.duss.easyproject.domain.entity.RegionCity
import app.duss.easyproject.domain.repository.RegionRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllCitiesUseCase(
    private val repository: RegionRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<RegionCity>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<RegionCity>> =
        repository.getAllCities(param1, param2)

}