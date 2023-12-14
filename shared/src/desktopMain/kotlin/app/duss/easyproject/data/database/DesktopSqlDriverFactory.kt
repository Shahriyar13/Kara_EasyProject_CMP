package app.duss.easyproject.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import app.duss.easyproject.core.database.AppDatabase
import org.koin.core.scope.Scope
import java.io.File


actual fun Scope.sqlDriverFactory(): SqlDriver {
    val databasePath = File(System.getProperty("java.io.tmpdir"), "${DatabaseConstants.name}.db")
    val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.path}")
    AppDatabase.Schema.create(driver)
    return driver
}