package app.duss.easyproject.domain.usecase.pi

import app.duss.easyproject.domain.entity.ProformaInvoice
import app.duss.easyproject.domain.repository.ProformaInvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllByProjectIdUseCase(
    private val repository: ProformaInvoiceRepository,
): BaseCoroutinesUseCaseMultiParam<Long, Int, List<ProformaInvoice>>() {

    override suspend fun execute(param1: Long, param2: Int): Result<List<ProformaInvoice>> =
        repository.getAllByProjectId(param1, param2)

}