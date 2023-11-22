package app.duss.easyproject.data.repository

import app.duss.easyproject.core.database.dao.PokemonInfoDao
import app.duss.easyproject.core.model.PokemonInfo
import app.duss.easyproject.core.network.client.ProjectClient
import app.duss.easyproject.data.toPokemonInfo
import app.duss.easyproject.data.toPokemonInfoEntity
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProjectRepositoryImpl: ProjectRepository, KoinComponent {

    private val projectClient by inject<ProjectClient>()
    private val pokemonInfoDao by inject<PokemonInfoDao>()

    override suspend fun getProjectList(page: Long): Result<List<Project>> {
        return try {
            val response = projectClient.getProjectList(page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getPokemonFlowByName(name: String): Result<PokemonInfo> {
        return try {
            val cachedPokemon = pokemonInfoDao.selectOneByName(name = name)

            if (cachedPokemon == null) {
                val response = projectClient.getPokemonByName(name = name)
                pokemonInfoDao.insert(response.toPokemonInfoEntity())

                Result.success(response)
            } else {
                Result.success(cachedPokemon.toPokemonInfo())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavoritePokemonListFlow(): Flow<List<Project>> {
        return flowOf(listOf())
    }

    override suspend fun updatePokemonFavoriteState(name: String, isFavorite: Boolean) {
        pokemonInfoDao.updateIsFavorite(
            name = name,
            isFavorite = isFavorite,
        )
    }

}