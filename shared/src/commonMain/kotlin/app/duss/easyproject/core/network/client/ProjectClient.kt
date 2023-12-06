package app.duss.easyproject.core.network.client

import app.duss.easyproject.core.network.NetworkConstants
import app.duss.easyproject.core.network.helper.handleErrors
import app.duss.easyproject.core.network.model.ServerResponse
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectCreateRequest
import app.duss.easyproject.domain.params.ProjectUpdateRequest
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProjectClient(
    private val httpClient: HttpClient
) {

    suspend fun getProjectList(
        page: Int,
    ): ServerResponse<List<Project>> {
        return handleErrors {
            httpClient.get(NetworkConstants.Project.getAll) {
                url {
                    parameters.append("page", page.toString())
//                    parameters.append("limit", PageSize.toString())
//                    parameters.append("offset", (page * PageSize).toString())
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun isProjectCodeAvailable(code: String): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.get(NetworkConstants.Project.validateCode) {
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
            httpClient.get(NetworkConstants.Project.getById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getProjectNewProject(): ServerResponse<Project> {
        return handleErrors {
            httpClient.get(NetworkConstants.Project.getNew) {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun createProject(
        params: ProjectCreateRequest,
    ): ServerResponse<Project> {
        return handleErrors {
            httpClient.post(NetworkConstants.Project.create) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun updateProject(
        params: ProjectUpdateRequest,
    ): ServerResponse<Project> {
        return handleErrors {
            httpClient.post(NetworkConstants.Project.update) {
                setBody(params)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun deleteProject(
        id: Long,
    ): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.delete(NetworkConstants.Project.deleteById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }
}