package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.params.UserLoginRequest
import app.duss.easyproject.domain.params.UserRequest

interface UserRepository {

    suspend fun loginUser(params: UserLoginRequest): Result<User>

    suspend fun getUser(): Result<User>

    suspend fun getToken(): String?

    suspend fun createUser(param: UserRequest): Result<User>

    suspend fun updateUser(param: UserRequest): Result<User>

}