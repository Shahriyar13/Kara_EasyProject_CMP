package app.duss.easyproject.data.network

object NetworkConstants {
    const val PageSize = 10

    const val baseUrl = "http://127.0.0.1:8080"
    private const val baseApiUrl = "$baseUrl/api"

    object Company {
        private const val route = "$baseApiUrl/company"
        const val getAll: String = "$route/v1/getAll"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"

        const val getAllCustomers: String = "$route/v1/getAllCustomers"
        const val getAllSuppliers: String = "$route/v1/getAllSuppliers"
        const val getAllFreightForwarders: String = "$route/v1/getAllFreightForwarders"

        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }
    object CustomerEnquiry {
        private const val route = "$baseApiUrl/ce"
        const val getAll: String = "$route/v1/getAll"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"
        const val getAllByProjectId: String = "$route/v1/getAllByProjectId"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }
    object Invoice {
        private const val route = "$baseApiUrl/invoice"
        const val getAll: String = "$route/v1/getAll"
        const val getAllByProjectId: String = "$route/v1/getAllByProjectId"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }
    object Packing {
        private const val route = "$baseApiUrl/packing"
        const val getAll: String = "$route/v1/getAll"
        const val getAllByProjectId: String = "$route/v1/getAllByProjectId"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }
    object Item {
        private const val route = "$baseApiUrl/item"
        const val getAll: String = "$route/v1/getAll"
        const val getById: String = "$route/v1/getById"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }
    object Person {
        private const val route = "$baseApiUrl/person"
        const val getAll: String = "$route/v1/getAll"
        const val getAllByCompanyId: String = "$route/v1/getAllByCompanyId"
        const val getById: String = "$route/v1/getById"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }

    object ProformaInvoice {
        private const val route = "$baseApiUrl/pi"
        const val getAll: String = "$route/v1/getAll"
        const val getAllByProjectId: String = "$route/v1/getAllByProjectId"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }

    object PurchaseOrder {
        private const val route = "$baseApiUrl/po"
        const val getAll: String = "$route/v1/getAll"
        const val getAllByProjectId: String = "$route/v1/getAllByProjectId"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }

    object Quotation {
        private const val route = "$baseApiUrl/quotation"
        const val getAll: String = "$route/v1/getAll"
        const val getAllByProjectId: String = "$route/v1/getAllByProjectId"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }

    object SupplierEnquiry {
        private const val route = "$baseApiUrl/se"
        const val getAll: String = "$route/v1/getAll"
        const val getAllByProjectId: String = "$route/v1/getAllByProjectId"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }
    object Project {
        private const val route = "$baseApiUrl/project"
        const val getAll: String = "$route/v1/getAll"
        const val validateCode: String = "$route/v1/validCode"
        const val getById: String = "$route/v1/getById"
        const val getNew: String = "$route/v1/getNew"
        const val getAllByCustomerEnquiryId: String = "$route/v1/getAllByCustomerEnquiryId"
        const val deleteById: String = "$route/v1/deleteById"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
    }
    object Region {
        private const val route = "$baseApiUrl/region"
        const val getCountryById: String = "$route/v1/getCountryById"
        const val getAllCountries: String = "$route/v1/getAllCountries"
        const val getStateById: String = "$route/v1/getStateById"
        const val getAllStates: String = "$route/v1/getAllStates"
        const val getCityById: String = "$route/v1/getCityById"
        const val getAllCities: String = "$route/v1/getAllCities"
        const val getCustomsPortById: String = "$route/v1/getCustomsPortById"
        const val getAllCustomsPorts: String = "$route/v1/getAllCustomsPorts"
    }
    object Attachment {
        private const val route = "$baseApiUrl/file_attachment"
        const val upload: String = "$route/v1/upload"
        const val deleteById: String = "$route/v1/deleteById"
    }
    object User {
        private const val route = "$baseApiUrl/auth"
        const val login: String = "$route/v1/login"
        const val create: String = "$route/v1/create"
        const val update: String = "$route/v1/update"
        const val changeRole: String = "$route/v1/changeRole"
    }
}