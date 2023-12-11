package app.duss.easyproject.data.repository

import app.duss.easyproject.data.database.dao.UserDao
import app.duss.easyproject.data.mapToDatabaseEntity
import app.duss.easyproject.data.mapToDomainEntity
import app.duss.easyproject.data.network.client.UserClient
import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.params.UserLoginRequest
import app.duss.easyproject.domain.params.UserRequest
import app.duss.easyproject.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepositoryImpl: UserRepository, KoinComponent {

    private val userClient by inject<UserClient>()
    private val userDao by inject<UserDao>()
    override suspend fun loginUser(params: UserLoginRequest): Result<User> {
        return try {
                val response = userClient.login(params)
                if (response.data != null) {
                    userDao.set(response.data.user.mapToDatabaseEntity(response.data.token))
                    getUser()
                } else {
                    Result.failure(Exception("Not found"))
                }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUser(): Result<User> {
        return try {
           val user = userDao.get()
           if (user != null) {
               Result.success(user.mapToDomainEntity())
           } else {
               Result.failure(Exception("Not found"))
           }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getToken(): String? {
        return try {
            val user = userDao.get()
            user?.token
        } catch (e: Exception) {
            null
        }
    }



    override suspend fun createUser(param: UserRequest): Result<User> {
        return try {
            val response = userClient.create(params = param)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(param: UserRequest): Result<User> {
        return try {
            val response = userClient.update(params = param)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}