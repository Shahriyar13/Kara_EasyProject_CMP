package app.duss.easyproject.core.database.di

import app.duss.easyproject.core.database.createDatabase
import app.duss.easyproject.core.database.dao.PokemonDao
import app.duss.easyproject.core.database.dao.PokemonInfoDao
import app.duss.easyproject.core.database.sqlDriverFactory
import org.koin.dsl.module

val databaseModule = module {
    factory { sqlDriverFactory() }
    single { createDatabase(driver = get()) }
    single { PokemonDao(pokemonDatabase = get()) }
    single { PokemonInfoDao(pokemonDatabase = get()) }
}