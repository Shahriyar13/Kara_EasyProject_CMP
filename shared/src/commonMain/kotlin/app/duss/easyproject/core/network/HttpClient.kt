package app.duss.easyproject.core.network

import app.duss.easyproject.domain.repository.UserRepository
import app.duss.easyproject.utils.appDispatchers
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

internal fun createHttpClient(userRepository: UserRepository): HttpClient {
    return createPlatformHttpClient().config {
        install(Resources)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                useAlternativeNames = true
                ignoreUnknownKeys = true
                encodeDefaults = false
            })
        }

        var token: String? = null
        val coroutineScope = CoroutineScope(appDispatchers.main)
        coroutineScope.launch {
            token = userRepository.getToken()
        }
        if (!token.isNullOrEmpty()) {
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(token!!, "token")
                    }
                }
            }
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
}