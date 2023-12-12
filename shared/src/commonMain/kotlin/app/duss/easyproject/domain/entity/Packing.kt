package app.duss.easyproject.domain.entity

import app.duss.easyproject.domain.enums.PackingType
import app.duss.easyproject.domain.enums.PaymentType
import app.duss.easyproject.domain.enums.TransportType
import kotlinx.serialization.Serializable

@Serializable
data class Packing(
    val standardNumber: String?,
    val cbNumber: String?,
    val loadingPort: RegionCustomsPort?,
    val dischargingPort: RegionCustomsPort?,
    val insuranceCompany: Insurance?,
    val insurancePolicyNumber: String?,
    val buyerCommercialCardNumber: String?,
    val termOfDelivery: String?,
    val originCountry: RegionCountry?,
    val paymentType: PaymentType = PaymentType.Unknown,
    val packingType: PackingType = PackingType.Unknown,
    val transportType: TransportType = TransportType.Unknown,
    val notAssignedBoxes: List<Box> = listOf(),
    val container: List<Container> = listOf(),
    val fileAttachments: List<FileAttachment> = listOf(),
    val project: Project,
    override val code: String?,
    override val codeExtension: String?,
    override val time: Long?,
    override val sendTime: Long?,
    override val annualId: Int?,
    override val id: Long?,
    override val creationTime: Long,
    override val modificationTime: Long?,
    override val createdBy: String,
    override val modifiedBy: String?,
    override val creatorId: Long?,
    override val modifierId: Long?
): BaseDocumentEntity()
