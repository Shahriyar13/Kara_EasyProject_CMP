package app.duss.easyproject.core.di

import app.duss.easyproject.data.database.createDatabase
import app.duss.easyproject.data.database.dao.UserDao
import app.duss.easyproject.data.database.sqlDriverFactory
import org.koin.dsl.module

val databaseModule = module {
    factory { sqlDriverFactory() }
    single { createDatabase(driver = get()) }
    single { UserDao(appDatabase = get()) }
}