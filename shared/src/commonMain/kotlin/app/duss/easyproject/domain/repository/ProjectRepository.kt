package app.duss.easyproject.domain.repository

import app.duss.easyproject.core.model.Project
import app.duss.easyproject.core.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    suspend fun getProjectList(page: Long): Result<List<Project>>

    suspend fun getPokemonFlowByName(name: String): Result<PokemonInfo>

    suspend fun getFavoritePokemonListFlow(): Flow<List<Project>>

    suspend fun updatePokemonFavoriteState(name: String, isFavorite: Boolean)

}