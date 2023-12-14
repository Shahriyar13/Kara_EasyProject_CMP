package app.duss.easyproject.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java

actual fun createPlatformHttpClient(): HttpClient {
    return HttpClient(Java)
}