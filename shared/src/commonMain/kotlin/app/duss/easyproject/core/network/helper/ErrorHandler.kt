package app.duss.easyproject.core.network.helper

import app.duss.easyproject.core.network.errors.ServerError
import app.duss.easyproject.core.network.errors.ServerException
import app.duss.easyproject.appDispatchers
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.withContext

suspend inline fun <reified T> handleErrors(
    crossinline response: suspend () -> HttpResponse
): T = withContext(appDispatchers.io) {

    val result = try {
        response()
    } catch(e: IOException) {
        throw ServerException(ServerError.ServiceUnavailable)
    }

    when(result.status.value) {
        in 200..299 -> Unit
        in 400..499 -> throw ServerException(ServerError.ClientError)
        500 -> throw ServerException(ServerError.ServerError)
        else -> throw ServerException(ServerError.UnknownError)
    }

    return@withContext try {
        result.body()
    } catch(e: Exception) {
        throw ServerException(ServerError.ServerError)
    }

}