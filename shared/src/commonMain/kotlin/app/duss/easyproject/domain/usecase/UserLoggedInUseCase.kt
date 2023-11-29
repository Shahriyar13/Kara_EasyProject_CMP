package app.duss.easyproject.domain.usecase

import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.repository.UserRepository

class UserLoggedInUseCase(
    private val repository: UserRepository,
): BaseCoroutinesUseCase<Unit?, User>() {
    override suspend fun execute(param: Unit?): Result<User> = repository.getUser()

}