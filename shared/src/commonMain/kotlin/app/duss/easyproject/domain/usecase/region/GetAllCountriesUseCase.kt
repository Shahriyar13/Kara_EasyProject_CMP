package app.duss.easyproject.domain.usecase.region

import app.duss.easyproject.domain.entity.RegionCountry
import app.duss.easyproject.domain.repository.RegionRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllCountriesUseCase(
    private val repository: RegionRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<RegionCountry>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<RegionCountry>> =
        repository.getAllCountries(param1, param2)

}