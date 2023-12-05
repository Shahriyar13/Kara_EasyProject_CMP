package app.duss.easyproject.data.repository

import app.duss.easyproject.core.network.client.ProjectClient
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.params.ProjectCreateRequest
import app.duss.easyproject.domain.params.ProjectUpdateRequest
import app.duss.easyproject.domain.repository.ProjectRepository
import io.ktor.util.date.GMTDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProjectRepositoryImpl: ProjectRepository, KoinComponent {

    private val projectClient by inject<ProjectClient>()
//    private val pokemonInfoDao by inject<PokemonInfoDao>()

    override suspend fun getProjectList(page: Long): Result<List<Project>> {
        return Result.success(
            listOf(
                Project(id = 1, code = "23001", time = GMTDate().timestamp, annualId = 1),
                Project(id = 2, code = "23002", time = GMTDate().timestamp, annualId = 2),
                Project(id = 3, code = "23003", time = GMTDate().timestamp, annualId = 3),
            )
        )
//        return try {
//            val response = projectClient.getProjectList(page = page)
//            Result.success(response.data ?: emptyList())
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Result.failure(e)
//        }
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
        return Result.success(
                Project(id = 1, code = "23001", time = GMTDate().timestamp, annualId = 1),
        )
//        return try {
//            if (id != null) {
//                val response = projectClient.getProjectById(id = id)
//                if (response.data != null) {
//                    Result.success(response.data)
//                } else {
//                    Result.failure(Exception("Not found"))
//                }
//            } else {
//                val response = projectClient.getProjectNewProject()
//                if (response.data != null) {
//                    Result.success(response.data)
//                } else {
//                    Result.failure(Exception("Not found"))
//                }
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
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