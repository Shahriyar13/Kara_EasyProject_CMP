package app.duss.easyproject.domain.usecase.pi

import app.duss.easyproject.domain.entity.ProformaInvoice
import app.duss.easyproject.domain.repository.ProformaInvoiceRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllUseCase(
    private val repository: ProformaInvoiceRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<ProformaInvoice>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<ProformaInvoice>> =
        repository.getAll(param1, param2)

}