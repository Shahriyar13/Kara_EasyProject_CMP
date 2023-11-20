package app.duss.easyproject.data.repository

import app.duss.easyproject.core.database.dao.PokemonDao
import app.duss.easyproject.core.database.dao.PokemonInfoDao
import app.duss.easyproject.core.model.Project
import app.duss.easyproject.core.model.PokemonInfo
import app.duss.easyproject.core.network.client.PokemonClient
import app.duss.easyproject.data.toPokemon
import app.duss.easyproject.data.toPokemonEntity
import app.duss.easyproject.data.toPokemonInfo
import app.duss.easyproject.data.toPokemonInfoEntity
import app.duss.easyproject.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PokemonRepositoryImpl: ProjectRepository, KoinComponent {

    private val pokemonClient by inject<PokemonClient>()
    private val pokemonDao by inject<PokemonDao>()
    private val pokemonInfoDao by inject<PokemonInfoDao>()

    override suspend fun getProjectList(page: Long): Result<List<Project>> {
        return try {
            val cachedPokemonList = pokemonDao.selectAllByPage(page)

            if (cachedPokemonList.isEmpty()) {
                val response = pokemonClient.getPokemonList(page = page)
                response.results.forEach { pokemon ->
                    pokemonDao.insert(pokemon.toPokemonEntity(page))
                }

                Result.success(pokemonDao.selectAllByPage(page).map { it.toPokemon() })
            } else {
                Result.success(cachedPokemonList.map { it.toPokemon() })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getPokemonFlowByName(name: String): Result<PokemonInfo> {
        return try {
            val cachedPokemon = pokemonInfoDao.selectOneByName(name = name)

            if (cachedPokemon == null) {
                val response = pokemonClient.getPokemonByName(name = name)
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
        return pokemonInfoDao.selectAllFavorite().map { list ->
            list.map { it.toPokemon() }
        }
    }

    override suspend fun updatePokemonFavoriteState(name: String, isFavorite: Boolean) {
        pokemonInfoDao.updateIsFavorite(
            name = name,
            isFavorite = isFavorite,
        )
    }

}