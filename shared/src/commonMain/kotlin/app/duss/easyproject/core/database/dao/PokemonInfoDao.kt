package app.duss.easyproject.core.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.duss.easyproject.appDispatchers
import appdusseasyproject.PokemonInfoEntity
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonInfoDao(
    private val pokemonDatabase: app.duss.easyproject.core.database.PokemonDatabase
) {
    private val query get() = pokemonDatabase.pokemonInfoEntityQueries

    suspend fun selectOneByName(name: String) = withContext(appDispatchers.io) {
        query.selectOneByName(name = name).executeAsOneOrNull()
    }

    suspend fun selectAllFavorite() = withContext(appDispatchers.io) {
        query.selectAllFavorite().asFlow().map { it.executeAsList() }
    }

    suspend fun insert(pokemonInfoEntity: PokemonInfoEntity) = withContext(appDispatchers.io) {
        query.insert(pokemonInfoEntity)
    }

    suspend fun updateIsFavorite(name: String, isFavorite: Boolean) = withContext(appDispatchers.io) {
        query.updateIsFavorite(
            isFavorite = if (isFavorite) 1 else 0,
            name = name
        )
    }
}
