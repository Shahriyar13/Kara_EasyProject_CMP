package app.duss.easyproject.core.di

import app.duss.easyproject.core.database.di.databaseModule
import app.duss.easyproject.core.network.di.networkModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            databaseModule,
            networkModule(enableNetworkLogs),
            repositoryModule,
            useCaseModule,
        )
    }