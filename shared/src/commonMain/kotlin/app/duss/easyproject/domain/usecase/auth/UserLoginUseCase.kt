package app.duss.easyproject.domain.usecase.auth

import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.params.UserLoginRequest
import app.duss.easyproject.domain.repository.UserRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class UserLoginUseCase(
    private val repository: UserRepository,
): BaseCoroutinesUseCase<UserLoginRequest, User?>() {

    override suspend fun execute(param: UserLoginRequest): Result<User?> =
        repository.loginUser(param)

}