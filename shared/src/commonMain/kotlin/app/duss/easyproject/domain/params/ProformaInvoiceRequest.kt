package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.enums.PackingType
import app.duss.easyproject.domain.enums.TransportType
import kotlinx.serialization.Serializable


@Serializable
data class ProformaInvoiceRequest (
    val id: Long? = null,
    val code: String?,
    val codeExtension: String?,
    val annualId: Int?,
    val projectId: Long,
    val time: Long,
    val sendTime: Long?,

    var totalPackingPrice: Double?,
    var totalInspectionPrice: Double?,
    var totalFreightPrice: Double?,
    var totalNetWeight: Double?,
    var totalGrossWeight: Double?,
    var buyerReference: String?,
    var sellerReference: String?,
    var termsOfDelivery: String?,
    var timeOfDelivery: String?,
    var termsOfPayment: String?,

    var countryOfOriginId: Long? = null,
    var countryOfBeneficiaryId: Long? = null,
    var countryOfDestinationId: Long? = null,
    var cityOfFinalDeliveryId: Long? = null,

    var placeOfLoading: String? = null,
    var placeOfDischarge: String? = null,
    var isPartialShipmentAllowed: Boolean?,
    var isTransshipmentAllowed: Boolean?,

    val packingType: PackingType? = PackingType.Unknown,
    val transportType: TransportType? = TransportType.Unknown,

    var validTime: Long?,
    var note: String? = null,
    var termsAndConditions: String? = null,
    val confirmTime: Long? = null,

    val priceMargin: Float = 0.0F,

    val quotationItems: List<QuotationItemRequest> = ArrayList(),

)