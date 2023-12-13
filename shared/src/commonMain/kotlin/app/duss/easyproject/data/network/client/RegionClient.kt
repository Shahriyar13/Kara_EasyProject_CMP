package app.duss.easyproject.data.network.client

import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.data.network.dto.ServerResponse
import app.duss.easyproject.data.network.helper.handleErrors
import app.duss.easyproject.domain.entity.RegionCity
import app.duss.easyproject.domain.entity.RegionCountry
import app.duss.easyproject.domain.entity.RegionCustomsPort
import app.duss.easyproject.domain.entity.RegionState
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RegionClient(
    private val httpClient: HttpClient
) {

    suspend fun getAllCities(
        query: String?,
        page: Int,
    ): ServerResponse<List<RegionCity>> {
        return handleErrors {
            httpClient.get(NetworkConstants.RegionAPIs.getAllCities) {
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
    suspend fun getCityById(
        id: Long,
    ): ServerResponse<RegionCity> {
        return handleErrors {
            httpClient.get(NetworkConstants.RegionAPIs.getCityById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getAllStates(
        query: String?,
        page: Int,
    ): ServerResponse<List<RegionState>> {
        return handleErrors {
            httpClient.get(NetworkConstants.RegionAPIs.getAllStates) {
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
    suspend fun getStateById(
        id: Long,
    ): ServerResponse<RegionState> {
        return handleErrors {
            httpClient.get(NetworkConstants.RegionAPIs.getStateById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getAllCountries(
        query: String?,
        page: Int,
    ): ServerResponse<List<RegionCountry>> {
        return handleErrors {
            httpClient.get(NetworkConstants.RegionAPIs.getAllCountries) {
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
    suspend fun getCountryById(
        id: Long,
    ): ServerResponse<RegionCountry> {
        return handleErrors {
            httpClient.get(NetworkConstants.RegionAPIs.getCountryById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getAllCustomsPorts(
        query: String?,
        page: Int,
    ): ServerResponse<List<RegionCustomsPort>> {
        return handleErrors {
            httpClient.get(NetworkConstants.RegionAPIs.getAllCustomsPorts) {
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
    suspend fun getCustomsPortById(
        id: Long,
    ): ServerResponse<RegionCustomsPort> {
        return handleErrors {
            httpClient.get(NetworkConstants.RegionAPIs.getCustomsPortById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

}