package app.duss.easyproject.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.duss.easyproject.core.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return AndroidSqliteDriver(AppDatabase.Schema, androidContext(), "${DatabaseConstants.name}.db")
}