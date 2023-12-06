package app.duss.easyproject.data.repository

import app.duss.easyproject.core.network.client.ProjectClient
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectCreateRequest
import app.duss.easyproject.domain.params.ProjectUpdateRequest
import app.duss.easyproject.domain.repository.ProjectRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProjectRepositoryImpl : ProjectRepository, KoinComponent {

    private val projectClient by inject<ProjectClient>()
//    private val pokemonInfoDao by inject<PokemonInfoDao>()

    override suspend fun getAll(page: Int): Result<List<Project>> {
        return try {
            val response = projectClient.getProjectList(page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun isCodeAvailable(code: String): Result<Boolean> {
        return try {
            val response = projectClient.isProjectCodeAvailable(code = code)
            Result.success(response.data ?: false)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getById(id: Long): Result<Project> {
        return try {
            val response = projectClient.getProjectById(id = id)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNew(): Result<Project> {
        return try {

            val response = projectClient.getProjectNewProject()
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Error"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun create(param: ProjectCreateRequest): Result<Project> {
        return try {
            val response = projectClient.createProject(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Creating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(param: ProjectUpdateRequest): Result<Project> {
        return try {
            val response = projectClient.updateProject(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Updating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(id: Long): Result<Boolean> {
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