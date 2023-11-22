package app.duss.easyproject.core.network.errors

enum class ServerError {
    ServiceUnavailable,
    ClientError,
    ServerError,
    UnknownError
}

class ServerException(error: ServerError): Exception(
    "Something goes wrong: $error"
)