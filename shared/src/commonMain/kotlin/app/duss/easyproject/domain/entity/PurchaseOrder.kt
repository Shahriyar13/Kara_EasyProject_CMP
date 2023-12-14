package app.duss.easyproject.domain.entity

import app.duss.easyproject.domain.enums.PackingType
import app.duss.easyproject.domain.enums.TransportType
import kotlinx.serialization.Serializable

@Serializable
data class PurchaseOrder(
    val totalPackingPrice: Double?,
    val totalInspectionPrice: Double?,
    val totalFreightPrice: Double?,
    val totalNetWeight: Double?,
    val totalGrossWeight: Double?,
    val buyerReference: String?,
    val sellerReference: String?,
    val termsOfDelivery: String?,
    val timeOfDelivery: String?,
    val termsOfPayment: String?,
    val countryOfOrigin: RegionCountry?,
    val countryOfBeneficiary: RegionCountry?,
    val countryOfDestination: RegionCountry?,
    val cityOfFinalDelivery: RegionCity?,
    val placeOfLoading: String?,
    val placeOfDischarge: String?,
    val isPartialShipmentAllowed: Boolean?,
    val isTransshipmentAllowed: Boolean?,
    val transportType: TransportType? = TransportType.Unknown,
    val packingType: PackingType? = PackingType.Unknown,
    val confirmTime: Long?,
    val note: String?,
    val termsAndConditions: String?,
    val quotationItems: List<QuotationItem>? = listOf(),
    val quotation: Quotation,
    val project: Project,
    val fileAttachments: List<FileAttachment> = ArrayList(),
    override val code: String,
    override val codeExtension: String?,
    override val annualId: Int,
    override val time: Long,
    override val sendTime: Long?,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?,
): BaseDocumentEntity()
