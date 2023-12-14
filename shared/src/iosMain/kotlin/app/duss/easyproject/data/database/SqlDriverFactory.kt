package app.duss.easyproject.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.duss.easyproject.core.database.AppDatabase
import org.koin.core.scope.Scope


actual fun Scope.sqlDriverFactory(): SqlDriver {
    return NativeSqliteDriver(AppDatabase.Schema, "${DatabaseConstants.name}.db")
}