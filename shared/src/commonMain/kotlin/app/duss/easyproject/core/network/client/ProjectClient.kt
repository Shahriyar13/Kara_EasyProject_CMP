package app.duss.easyproject.core.network.client

import app.duss.easyproject.core.model.PokemonInfo
import app.duss.easyproject.core.network.NetworkConstants
import app.duss.easyproject.core.network.helper.handleErrors
import app.duss.easyproject.core.network.model.PokemonResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProjectClient(
    private val httpClient: HttpClient
) {

    suspend fun getProjectList(
        page: Long,
    ): PokemonResponse {
        return handleErrors {
            httpClient.get(NetworkConstants.Project.getAll) {
                url {
                    parameters.append("limit", PageSize.toString())
                    parameters.append("offset", (page * PageSize).toString())
                }
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getPokemonByName(
        name: String,
    ): PokemonInfo {
        return handleErrors {
            httpClient.get(NetworkConstants.Project.getById) {

                contentType(ContentType.Application.Json)
            }
        }
    }

    companion object {
        private const val PageSize = 20
    }

}