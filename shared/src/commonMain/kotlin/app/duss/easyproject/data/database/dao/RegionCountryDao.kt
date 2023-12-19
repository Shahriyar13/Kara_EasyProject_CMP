package app.duss.easyproject.data.database.dao

import app.duss.easyproject.core.database.AppDatabase
import app.duss.easyproject.core.utils.appDispatchers
import appdusseasyproject.RegionCountryEntity
import kotlinx.coroutines.withContext

class RegionCountryDao(
    private val appDatabase: AppDatabase
) {
    private val query get() = appDatabase.regionCountryEntityQueries

    suspend fun getById(id: Long) = withContext(appDispatchers.io) {
        query.getById(id).executeAsOneOrNull()
    }

    suspend fun getAll() = withContext(appDispatchers.io) {
        query.getAll().executeAsList()
    }

    suspend fun set(entity: RegionCountryEntity) = withContext(appDispatchers.io) {
        query.insert(entity)
    }

    suspend fun set(entities: List<RegionCountryEntity>) = withContext(appDispatchers.io) {
        entities.forEach {
            query.insert(it)
        }
    }
}
