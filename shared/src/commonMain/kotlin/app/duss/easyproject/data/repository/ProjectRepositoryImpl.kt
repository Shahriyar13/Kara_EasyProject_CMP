package app.duss.easyproject.data.repository

import app.duss.easyproject.core.network.client.ProjectClient
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectCreateRequest
import app.duss.easyproject.domain.params.ProjectUpdateRequest
import app.duss.easyproject.domain.repository.ProjectRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProjectRepositoryImpl: ProjectRepository, KoinComponent {

    private val projectClient by inject<ProjectClient>()
//    private val pokemonInfoDao by inject<PokemonInfoDao>()

    override suspend fun getProjectList(page: Int): Result<List<Project>> {
        return try {
            val response = projectClient.getProjectList(page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
    override suspend fun isProjectCodeAvailable(code: String): Result<Boolean> {
        return try {
            val response = projectClient.isProjectCodeAvailable(code = code)
            Result.success(response.data ?: false)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getProjectById(id: Long?): Result<Project> {
        return try {
            if (id != null) {
                val response = projectClient.getProjectById(id = id)
                if (response.data != null) {
                    Result.success(response.data)
                } else {
                    Result.failure(Exception("Not found"))
                }
            } else {
                val response = projectClient.getProjectNewProject()
                if (response.data != null) {
                    Result.success(response.data)
                } else {
                    Result.failure(Exception("Not found"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createProject(param: ProjectCreateRequest): Result<Project?> {
        return try {
            val response = projectClient.createProject(params = param)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProject(param: ProjectUpdateRequest): Result<Project?> {
        return try {
            val response = projectClient.updateProject(params = param)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProject(id: Long): Result<Boolean> {
        return try {
            val response = projectClient.deleteProject(id = id)
            Result.success(response.data ?: false)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

//    override suspend fun getFavoritePokemonListFlow(): Flow<List<Project>> {
//        return flowOf(listOf())
//    }
//
//    override suspend fun updatePokemonFavoriteState(name: String, isFavorite: Boolean) {
//        pokemonInfoDao.updateIsFavorite(
//            name = name,
//            isFavorite = isFavorite,
//        )
//    }

}