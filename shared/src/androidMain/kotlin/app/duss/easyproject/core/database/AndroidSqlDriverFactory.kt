package app.duss.easyproject.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return AndroidSqliteDriver(app.duss.easyproject.core.database.PokemonDatabase.Schema, androidContext(), "${DatabaseConstants.name}.db")
}