package app.duss.easyproject.data.network.client

import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.data.network.dto.ServerResponse
import app.duss.easyproject.data.network.helper.handleErrors
import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.params.PersonRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PersonClient(
    private val httpClient: HttpClient
) {

    suspend fun getAll(
        query: String?,
        page: Int,
    ): ServerResponse<List<Person>> {
        return handleErrors {
            httpClient.get(NetworkConstants.Person.getAll) {
                url {
                    parameters.append("page", page.toString())
                   query?.let {
                        parameters.append("query", it)
                    }
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getAllByCompanyId(
        companyId: Long,
        page: Int,
    ): ServerResponse<List<Person>> {
        return handleErrors {
            httpClient.get(NetworkConstants.Person.getAllByCompanyId) {
                url {
                    parameters.append("page", page.toString())
                    parameters.append("companyId", companyId.toString())
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getById(
        id: Long,
    ): ServerResponse<Person> {
        return handleErrors {
            httpClient.get(NetworkConstants.Person.getById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun create(
        params: PersonRequest,
    ): ServerResponse<Person> {
        return handleErrors {
            httpClient.post(NetworkConstants.Person.create) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun update(
        params: PersonRequest,
    ): ServerResponse<Person> {
        return handleErrors {
            httpClient.put(NetworkConstants.Person.update) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun deleteById(
        id: Long,
    ): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.delete(NetworkConstants.Person.deleteById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }
}