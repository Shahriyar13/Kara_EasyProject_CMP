package app.duss.easyproject.core.database

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope

expect fun Scope.sqlDriverFactory(): SqlDriver
fun createDatabase(driver: SqlDriver): app.duss.easyproject.core.database.PokemonDatabase {
    val database = app.duss.easyproject.core.database.PokemonDatabase(
        driver = driver,
    )

    return database
}