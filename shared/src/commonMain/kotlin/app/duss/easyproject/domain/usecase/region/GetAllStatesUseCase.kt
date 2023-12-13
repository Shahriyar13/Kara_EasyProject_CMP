package app.duss.easyproject.domain.usecase.region

import app.duss.easyproject.domain.entity.RegionState
import app.duss.easyproject.domain.repository.RegionRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllStatesUseCase(
    private val repository: RegionRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<RegionState>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<RegionState>> =
        repository.getAllStates(param1, param2)

}