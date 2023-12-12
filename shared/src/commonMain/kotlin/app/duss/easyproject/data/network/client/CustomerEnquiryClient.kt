package app.duss.easyproject.data.network.client

import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.data.network.dto.ServerResponse
import app.duss.easyproject.data.network.helper.handleErrors
import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.params.CustomerEnquiryRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CustomerEnquiryClient(
    private val httpClient: HttpClient
) {

    suspend fun getAll(
        query: String?,
        page: Int,
    ): ServerResponse<List<CustomerEnquiry>> {
        return handleErrors {
            httpClient.get(NetworkConstants.CustomerEnquiry.getAll) {
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

    suspend fun getAllByProjectId(
        projectId: Long,
        page: Int,
    ): ServerResponse<List<CustomerEnquiry>> {
        return handleErrors {
            httpClient.get(NetworkConstants.CustomerEnquiry.getAllByProjectId) {
                url {
                    parameters.append("page", page.toString())
                    parameters.append("projectId", projectId.toString())
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun validateCode(code: String): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.get(NetworkConstants.CustomerEnquiry.validateCode) {
                url {
                    parameters.append("code", code)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getById(
        id: Long,
    ): ServerResponse<CustomerEnquiry> {
        return handleErrors {
            httpClient.get(NetworkConstants.CustomerEnquiry.getById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun create(
        params: CustomerEnquiryRequest,
    ): ServerResponse<CustomerEnquiry> {
        return handleErrors {
            httpClient.post(NetworkConstants.CustomerEnquiry.create) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun update(
        params: CustomerEnquiryRequest,
    ): ServerResponse<CustomerEnquiry> {
        return handleErrors {
            httpClient.put(NetworkConstants.CustomerEnquiry.update) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun deleteById(
        id: Long,
    ): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.delete(NetworkConstants.CustomerEnquiry.deleteById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }
}