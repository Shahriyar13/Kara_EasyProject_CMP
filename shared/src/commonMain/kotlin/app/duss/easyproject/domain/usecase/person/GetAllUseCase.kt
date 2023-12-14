package app.duss.easyproject.domain.usecase.person

import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.repository.PersonRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCaseMultiParam

class GetAllUseCase(
    private val repository: PersonRepository,
): BaseCoroutinesUseCaseMultiParam<String?, Int, List<Person>>() {

    override suspend fun execute(param1: String?, param2: Int): Result<List<Person>> =
        repository.getAll(param1, param2)

}