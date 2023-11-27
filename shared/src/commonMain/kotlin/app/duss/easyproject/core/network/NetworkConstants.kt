package app.duss.easyproject.core.network

object NetworkConstants {
    const val PageSize = 10

    const val baseUrl = "http://127.0.0.1:8080"
    private const val baseApiUrl = "$baseUrl/api"

    object Project {
        private const val route = "$baseApiUrl/project"
        val getAll: String = "$route/v1/getAll"
        val validateCode: String = "$route/v1/validCode"
        val getById: String = "$route/v1/getById"
        val getNew: String = "$route/v1/getNew"
        val getAllByCustomerEnquiryId: String = "$route/v1/getAllByCustomerEnquiryId"
        val deleteById: String = "$route/v1/deleteById"
        val deleteAll: String = "$route/v1/deleteAll"
        val create: String = "$route/v1/create"
        val update: String = "$route/v1/update"
    }
    object User {
        private const val route = "$baseApiUrl/auth"
        val login: String = "$route/v1/login"
        val create: String = "$route/v1/create"
        val update: String = "$route/v1/update"
        val changeRole: String = "$route/v1/changeRole"
    }
}