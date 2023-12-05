package app.duss.easyproject.core.network

object NetworkConstants {
    const val PageSize = 10

    const val baseUrl = "http://127.0.0.1:8080"
    private const val baseApiUrl = "$baseUrl/api"

    object Project {
        private const val route = "$baseApiUrl/project"
        const val getAll: String = "$route/v1/getAll"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"
        const val getNew: String = "$route/v1/getNew"
        const val getAllByCustomerEnquiryId: String = "$route/v1/getAllByCustomerEnquiryId"
        const val deleteById: String = "$route/v1/deleteById"
        const val deleteAll: String = "$route/v1/deleteAll"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }
    object User {
        private const val route = "$baseApiUrl/auth"
        const val login: String = "$route/v1/login"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
        const val changeRole: String = "$route/v1/changeRole"
    }
}