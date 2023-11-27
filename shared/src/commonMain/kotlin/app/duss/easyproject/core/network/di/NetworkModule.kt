package app.duss.easyproject.core.network.di

import app.duss.easyproject.core.network.client.ProjectClient
import app.duss.easyproject.core.network.client.UserClient
import app.duss.easyproject.core.network.createHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: (enableLogging: Boolean) -> Module get() = { enableLogging ->
    module {
        single { createHttpClient(get()) }
        single { ProjectClient(httpClient = get()) }
        single { UserClient(httpClient = get()) }
    }
}