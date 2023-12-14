package app.duss.easyproject.data.database.dao

import app.duss.easyproject.core.database.AppDatabase
import app.duss.easyproject.core.utils.appDispatchers
import appdusseasyproject.UserEntity
import kotlinx.coroutines.withContext

class UserDao(
    private val appDatabase: AppDatabase
) {
    private val query get() = appDatabase.userEntityQueries

    suspend fun get() = withContext(appDispatchers.io) {
        query.get().executeAsOneOrNull()
    }

    suspend fun set(pokemonInfoEntity: UserEntity) = withContext(appDispatchers.io) {
        query.delete()
        query.insert(pokemonInfoEntity)
    }
}
