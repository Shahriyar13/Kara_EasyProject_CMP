package app.duss.easyproject.data.network.client

import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.data.network.dto.ServerResponse
import app.duss.easyproject.data.network.helper.handleErrors
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProjectClient(
    private val httpClient: HttpClient
) {

    suspend fun getProjectList(
        query: String?,
        page: Int,
    ): ServerResponse<List<Project>> {
        return handleErrors {
            httpClient.get(NetworkConstants.ProjectAPIs.getAll) {
                url {
                    query?.let {
                        parameters.append("query", it)
                    }
                    parameters.append("page", page.toString())
//                    parameters.append("limit", PageSize.toString())
//                    parameters.append("offset", (page * PageSize).toString())
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun validateCode(code: String): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.get(NetworkConstants.ProjectAPIs.validateCode) {
                url {
                    parameters.append("code", code)
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getProjectById(
        id: Long,
    ): ServerResponse<Project> {
        return handleErrors {
            httpClient.get(NetworkConstants.ProjectAPIs.getById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getProjectNewProject(): ServerResponse<Project> {
        return handleErrors {
            httpClient.get(NetworkConstants.ProjectAPIs.getNew) {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun createProject(
        params: ProjectRequest,
    ): ServerResponse<Project> {
        return handleErrors {
            httpClient.post(NetworkConstants.ProjectAPIs.create) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun updateProject(
        params: ProjectRequest,
    ): ServerResponse<Project> {
        return handleErrors {
            httpClient.put(NetworkConstants.ProjectAPIs.update) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun deleteProject(
        id: Long,
    ): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.delete(NetworkConstants.ProjectAPIs.deleteById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }
}