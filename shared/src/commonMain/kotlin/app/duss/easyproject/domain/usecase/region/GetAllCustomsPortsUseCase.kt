package app.duss.easyproject.domain.usecase.region

import app.duss.easyproject.domain.entity.RegionCustomsPort
import app.duss.easyproject.domain.repository.RegionRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllCustomsPortsUseCase(
    private val repository: RegionRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<RegionCustomsPort>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<RegionCustomsPort>> =
        repository.getAllCustomsPorts(param1, param2)

}