package app.duss.easyproject.data.database.dao

import app.duss.easyproject.core.database.AppDatabase
import app.duss.easyproject.core.utils.appDispatchers
import appdusseasyproject.RegionStateEntity
import kotlinx.coroutines.withContext

class RegionStateDao(
    private val appDatabase: AppDatabase
) {
    private val query get() = appDatabase.regionStateEntityQueries

    suspend fun getById(id: Long) = withContext(appDispatchers.io) {
        query.getById(id).executeAsOneOrNull()
    }
    suspend fun getByCountryId(id: Long) = withContext(appDispatchers.io) {
        query.getCountryById(id).executeAsList()
    }

    suspend fun getAll() = withContext(appDispatchers.io) {
        query.getAll().executeAsList()
    }

    suspend fun set(entity: RegionStateEntity) = withContext(appDispatchers.io) {
        query.insert(entity)
    }

    suspend fun set(entities: List<RegionStateEntity>) = withContext(appDispatchers.io) {
        entities.forEach {
            query.insert(it)
        }
    }
}
