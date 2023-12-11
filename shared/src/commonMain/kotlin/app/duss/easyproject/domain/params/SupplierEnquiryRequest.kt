package app.duss.easyproject.domain.params

class SupplierEnquiryRequest (
    val id: Long? = null,
    val title: String?,
    val code: String?,
    val codeExtension: String?,
    val annualId: Int? = null,
    val supplier: CompanyRequest,
    val projectId: Long,
    val time: Long,
    val sendTime: Long?,
    val customerEnquiryItems: List<CustomerEnquiryItemRequest>? = ArrayList(),
    val intro: String?,
    val outro: String?,
    val requiredInformation: List<String> = listOf(),

    val personInCharge: PersonRequest? = null,
)
