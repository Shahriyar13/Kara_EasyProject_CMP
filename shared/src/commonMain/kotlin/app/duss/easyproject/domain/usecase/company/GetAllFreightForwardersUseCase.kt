package app.duss.easyproject.domain.usecase.company

import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.domain.repository.CompanyRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllFreightForwardersUseCase(
    private val repository: CompanyRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<Company>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<Company>> =
        repository.getAllFreightForwarders(param1, param2)

}