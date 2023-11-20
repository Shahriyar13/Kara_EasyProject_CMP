package app.duss.easyproject.core.network.errors

enum class KCommerceError {
    ServiceUnavailable,
    ClientError,
    ServerError,
    UnknownError
}

class KCommerceException(error: KCommerceError): Exception(
    "Something goes wrong: $error"
)