package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.domain.enums.PackingType
import app.duss.easyproject.domain.enums.PaymentType
import app.duss.easyproject.domain.enums.TransportType
import kotlinx.serialization.Serializable

@Serializable
data class PackingRequest (
    val id: Long? = null,
    val code: String?,
    val codeExtension: String?,
    val annualId: Int?,
    val projectId: Long,
    val time: Long?,
    val sendTime: Long?,

    val buyerCommercialCardNumber: String?,
    val termOfDelivery: String?,
    val standardNumber: String?,
    val cbNumber: String?,
    val loadingPort: RegionCustomsPortRequest?,
    val dischargingPort: RegionCustomsPortRequest?,
    val insuranceCompany: InsuranceRequest?,
    val insurancePolicyNumber: String?,
    val originCountryId: Long?,
    val paymentType: PaymentType? = PaymentType.Unknown,
    val packingType: PackingType? = PackingType.Unknown,
    val transportType: TransportType? = TransportType.Unknown,
    val notAssignedBoxes: List<BoxRequest>?,
    val containers: List<ContainerRequest>?,
)

fun Packing.toDto() = PackingRequest(
    id = id,
    code = code,
    annualId = annualId,
    codeExtension = codeExtension,
    time = time,
    projectId = project!!.id!!,
    sendTime = sendTime,
    buyerCommercialCardNumber = buyerCommercialCardNumber,
    termOfDelivery = termOfDelivery,
    standardNumber = standardNumber,
    cbNumber = cbNumber,
    loadingPort = loadingPort?.toDto(),
    dischargingPort = dischargingPort?.toDto(),
    insuranceCompany = insuranceCompany?.toDto(),
    insurancePolicyNumber = insurancePolicyNumber,
    originCountryId = originCountry?.id,
    paymentType = paymentType,
    packingType = packingType,
    transportType = transportType,
    notAssignedBoxes = boxes.map { it.toDto() },
    containers = containers.map { it.toDto() },
)