package app.duss.easyproject.domain.usecase.person

import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.params.PersonRequest
import app.duss.easyproject.domain.repository.PersonRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class CreateUseCase(
    private val repository: PersonRepository,
): BaseCoroutinesUseCase<PersonRequest, Person>() {
    override suspend fun execute(param: PersonRequest): Result<Person> =
        repository.create(param)

}