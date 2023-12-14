package app.duss.easyproject.domain.usecase.person

import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.repository.PersonRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class GetByIdUseCase(
    private val repository: PersonRepository,
): BaseCoroutinesUseCase<Long, Person>() {
    override suspend fun execute(param: Long): Result<Person> =
        repository.getById(param)

}