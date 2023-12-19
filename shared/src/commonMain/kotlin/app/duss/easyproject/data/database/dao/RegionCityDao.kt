package app.duss.easyproject.data.database.dao

import app.duss.easyproject.core.database.AppDatabase
import app.duss.easyproject.core.utils.appDispatchers
import appdusseasyproject.RegionCityEntity
import kotlinx.coroutines.withContext

class RegionCityDao(
    private val appDatabase: AppDatabase
) {
    private val query get() = appDatabase.regionCityEntityQueries

    suspend fun getById(id: Long): RegionCityEntity? = withContext(appDispatchers.io) {
        query.getStateById(id).executeAsOneOrNull()
    }
    suspend fun getByStateId(id: Long) = withContext(appDispatchers.io) {
        query.getStateById(id).executeAsList()
    }

    suspend fun getAll() = withContext(appDispatchers.io) {
        query.getAll().executeAsList()
    }

    suspend fun set(entity: RegionCityEntity) = withContext(appDispatchers.io) {
        query.insert(entity)
    }

    suspend fun set(entities: List<RegionCityEntity>) = withContext(appDispatchers.io) {
        entities.forEach {
            query.insert(it)
        }
    }
}
