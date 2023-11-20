package app.duss.easyproject.core.network.helper

import app.duss.easyproject.core.network.errors.KCommerceError
import app.duss.easyproject.core.network.errors.KCommerceException
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
        throw KCommerceException(KCommerceError.ServiceUnavailable)
    }

    when(result.status.value) {
        in 200..299 -> Unit
        in 400..499 -> throw KCommerceException(KCommerceError.ClientError)
        500 -> throw KCommerceException(KCommerceError.ServerError)
        else -> throw KCommerceException(KCommerceError.UnknownError)
    }

    return@withContext try {
        result.body()
    } catch(e: Exception) {
        throw KCommerceException(KCommerceError.ServerError)
    }

}