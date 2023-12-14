package app.duss.easyproject.data.database

import app.cash.sqldelight.db.SqlDriver
import app.duss.easyproject.core.database.AppDatabase
import org.koin.core.scope.Scope

expect fun Scope.sqlDriverFactory(): SqlDriver
fun createDatabase(driver: SqlDriver): AppDatabase = AppDatabase(driver = driver)
