package app.duss.easyproject.core.database.dao

import app.duss.easyproject.appDispatchers
import appdusseasyproject.PokemonEntity
import kotlinx.coroutines.withContext

class PokemonDao(
    private val pokemonDatabase: app.duss.easyproject.core.database.PokemonDatabase
) {
    private val query get() = pokemonDatabase.pokemonEntityQueries

    suspend fun selectAllByPage(page: Long) = withContext(appDispatchers.io) {
        query.selectAllByPage(page = page).executeAsList()
    }

    suspend fun insert(pokemonEntity: PokemonEntity) = withContext(appDispatchers.io) {
        query.insert(pokemonEntity)
    }
}
