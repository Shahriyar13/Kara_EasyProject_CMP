package app.duss.easyproject.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.core.scope.Scope


actual fun Scope.sqlDriverFactory(): SqlDriver {
    return NativeSqliteDriver(app.duss.easyproject.core.database.PokemonDatabase.Schema, "${DatabaseConstants.name}.db")
}