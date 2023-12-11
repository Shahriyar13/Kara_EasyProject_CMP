package app.duss.easyproject.data.network.client

import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.data.network.dto.ServerResponse
import app.duss.easyproject.data.network.dto.UserLoginDto
import app.duss.easyproject.data.network.helper.handleErrors
import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.params.UserLoginRequest
import app.duss.easyproject.domain.params.UserRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserClient(
    private val httpClient: HttpClient
) {
    suspend fun login(
        params: UserLoginRequest,
    ): ServerResponse<UserLoginDto> {
        return handleErrors {
            httpClient.post(NetworkConstants.User.login) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun create(
        params: UserRequest,
    ): ServerResponse<User> {
        return handleErrors {
            httpClient.post(NetworkConstants.User.create) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun update(
        params: UserRequest,
    ): ServerResponse<User> {
        return handleErrors {
            httpClient.post(NetworkConstants.User.update) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }
}