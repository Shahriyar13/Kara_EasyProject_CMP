package app.duss.easyproject.core.network

object NetworkConstants {
    const val PageSize = 10

    const val baseUrl = "http://127.0.0.1:8080"
    private const val baseApiUrl = "$baseUrl/api"

    object Project {
        private const val route = "$baseApiUrl/project"
        val getAll: String = "$route/v1/getAll"
        val getById: String = "$route/v1/getById"
        val getAllByCustomerEnquiryId: String = "$route/v1/getAllByCustomerEnquiryId"
        val deleteById: String = "$route/v1/deleteById"
        val deleteAll: String = "$route/v1/deleteAll"
        val add: String = "$route/v1/add"
    }
}