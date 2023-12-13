package app.duss.easyproject.data.network.client

import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.data.network.dto.ServerResponse
import app.duss.easyproject.data.network.helper.handleErrors
import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.domain.params.CompanyRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CompanyClient(
    private val httpClient: HttpClient
) {

    suspend fun getAll(
        query: String?,
        page: Int,
    ): ServerResponse<List<Company>> {
        return handleErrors {
            httpClient.get(NetworkConstants.CompanyAPIs.getAll) {
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

    suspend fun getAllCustomers(
        query: String?,
        page: Int,
    ): ServerResponse<List<Company>> {
        return handleErrors {
            httpClient.get(NetworkConstants.CompanyAPIs.getAllCustomers) {
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

    suspend fun getAllSuppliers(
        query: String?,
        page: Int,
    ): ServerResponse<List<Company>> {
        return handleErrors {
            httpClient.get(NetworkConstants.CompanyAPIs.getAllSuppliers) {
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

    suspend fun getAllFreightForwarders(
        query: String?,
        page: Int,
    ): ServerResponse<List<Company>> {
        return handleErrors {
            httpClient.get(NetworkConstants.CompanyAPIs.getAllFreightForwarders) {
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

    suspend fun validateCode(
        customerCode: String?,
        supplierCode: String?,
        freightForwarderCode: String?
    ): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.get(NetworkConstants.CompanyAPIs.validateCode) {
                url {
                    customerCode?.let {
                        parameters.append("customerCode", it)
                    }
                    supplierCode?.let {
                        parameters.append("supplierCode", it)
                    }
                    freightForwarderCode?.let {
                        parameters.append("freightForwarderCode", it)
                    }
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getById(
        id: Long,
    ): ServerResponse<Company> {
        return handleErrors {
            httpClient.get(NetworkConstants.CompanyAPIs.getById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun create(
        params: CompanyRequest,
    ): ServerResponse<Company> {
        return handleErrors {
            httpClient.post(NetworkConstants.CompanyAPIs.create) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun update(
        params: CompanyRequest,
    ): ServerResponse<Company> {
        return handleErrors {
            httpClient.put(NetworkConstants.CompanyAPIs.update) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun deleteById(
        id: Long,
    ): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.delete(NetworkConstants.CompanyAPIs.deleteById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }
}