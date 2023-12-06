package app.duss.easyproject.domain.usecase.auth

import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.repository.UserRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class UserLoggedInUseCase(
    private val repository: UserRepository,
): BaseCoroutinesUseCase<Unit?, User>() {
    override suspend fun execute(param: Unit?): Result<User> = repository.getUser()

}