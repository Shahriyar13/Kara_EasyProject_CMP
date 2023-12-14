package app.duss.easyproject.data.network.client

import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.data.network.dto.ServerResponse
import app.duss.easyproject.data.network.helper.handleErrors
import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.params.SupplierEnquiryRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SupplierEnquiryClient(
    private val httpClient: HttpClient
) {

    suspend fun getAll(
        query: String?,
        page: Int,
    ): ServerResponse<List<SupplierEnquiry>> {
        return handleErrors {
            httpClient.get(NetworkConstants.SupplierEnquiryAPIs.getAll) {
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
    ): ServerResponse<List<SupplierEnquiry>> {
        return handleErrors {
            httpClient.get(NetworkConstants.SupplierEnquiryAPIs.getAllByProjectId) {
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
            httpClient.get(NetworkConstants.SupplierEnquiryAPIs.validateCode) {
                url {
                    parameters.append("code", code)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getById(
        id: Long,
    ): ServerResponse<SupplierEnquiry> {
        return handleErrors {
            httpClient.get(NetworkConstants.SupplierEnquiryAPIs.getById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun create(
        params: SupplierEnquiryRequest,
    ): ServerResponse<SupplierEnquiry> {
        return handleErrors {
            httpClient.post(NetworkConstants.SupplierEnquiryAPIs.create) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun update(
        params: SupplierEnquiryRequest,
    ): ServerResponse<SupplierEnquiry> {
        return handleErrors {
            httpClient.put(NetworkConstants.SupplierEnquiryAPIs.update) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun deleteById(
        id: Long,
    ): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.delete(NetworkConstants.SupplierEnquiryAPIs.deleteById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }
}