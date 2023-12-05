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

    override suspend fun getProjectList(page: Int): Result<List<Project>> {
        return Result.success(
            if (page == 0) {
                listOf(
                    Project(id = 1, code = "23001", time = GMTDate().timestamp, annualId = 1),
                    Project(id = 2, code = "23002", time = GMTDate().timestamp, annualId = 2),
                    Project(id = 3, code = "23003", time = GMTDate().timestamp, annualId = 3),
                    Project(id = 4, code = "23004", time = GMTDate().timestamp, annualId = 4),
                    Project(id = 5, code = "23005", time = GMTDate().timestamp, annualId = 5),
                    Project(id = 6, code = "23006", time = GMTDate().timestamp, annualId = 6),
                    Project(id = 7, code = "23007", time = GMTDate().timestamp, annualId = 7),
                    Project(id = 8, code = "23008", time = GMTDate().timestamp, annualId = 8),
                    Project(id = 9, code = "23009", time = GMTDate().timestamp, annualId = 9),
                    Project(id = 10, code = "23010", time = GMTDate().timestamp, annualId = 10),
                    Project(id = 11, code = "23011", time = GMTDate().timestamp, annualId = 11),
                    Project(id = 12, code = "23012", time = GMTDate().timestamp, annualId = 12),
                    Project(id = 13, code = "23013", time = GMTDate().timestamp, annualId = 13),
                    Project(id = 14, code = "23014", time = GMTDate().timestamp, annualId = 14),
                    Project(id = 15, code = "23015", time = GMTDate().timestamp, annualId = 15),
                    Project(id = 16, code = "23016", time = GMTDate().timestamp, annualId = 16),
                    Project(id = 17, code = "23017", time = GMTDate().timestamp, annualId = 17),
                    Project(id = 18, code = "23018", time = GMTDate().timestamp, annualId = 18),
                    Project(id = 19, code = "23019", time = GMTDate().timestamp, annualId = 19),
                    Project(id = 20, code = "23020", time = GMTDate().timestamp, annualId = 20),
                )
            } else if (page == 1) {
                listOf(
                    Project(id = 21, code = "23021", time = GMTDate().timestamp, annualId = 21),
                    Project(id = 22, code = "23022", time = GMTDate().timestamp, annualId = 22),
                    Project(id = 23, code = "23023", time = GMTDate().timestamp, annualId = 23),
                )
            } else {
                listOf()
            }
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