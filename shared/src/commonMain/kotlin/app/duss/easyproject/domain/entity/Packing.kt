package app.duss.easyproject.domain.entity

import app.duss.easyproject.domain.enums.PackingType
import app.duss.easyproject.domain.enums.PaymentType
import app.duss.easyproject.domain.enums.TransportType
import kotlinx.serialization.Serializable

@Serializable
data class Packing(
    var standardNumber: String? = null,
    var cbNumber: String? = null,
    var loadingPort: RegionCustomsPort? = null,
    var dischargingPort: RegionCustomsPort? = null,
    var insuranceCompany: Insurance? = null,
    var insurancePolicyNumber: String? = null,
    var buyerCommercialCardNumber: String? = null,
    var termOfDelivery: String? = null,
    var originCountry: RegionCountry? = null,
    var paymentType: PaymentType = PaymentType.Unknown,
    var packingType: PackingType = PackingType.Unknown,
    var transportType: TransportType = TransportType.Unknown,
    var quotationItems: List<QuotationItem> = listOf(),
    var boxes: List<BoxOfItem> = listOf(),
    var containers: List<Container> = listOf(),
    var fileAttachments: List<FileAttachment> = listOf(),
    var project: Project? = null,
    override val code: String? = null,
    override val codeExtension: String? = null,
    override val time: Long? = null,
    override val sendTime: Long? = null,
    override val annualId: Int? = null,
    override val id: Long? = null,
    override val creationTime: Long? = null,
    override val modificationTime: Long? = null,
    override val createdBy: String? = null,
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null,
): BaseDocumentEntity() {

    // Function to find all quotationItems not fully contained in any boxes
    fun findQuotationItemsNotInBoxes(): List<QuotationItem> {
        val itemsInBoxes = boxes.flatMap { box ->
            box.boxItems.flatMap { boxItem ->
                List(boxItem.quantity) { boxItem.quotationItem }
            }
        }
        val itemQuantitiesInBoxes = itemsInBoxes.groupBy { it }.mapValues { it.value.size }
        return quotationItems.filter { item -> (itemQuantitiesInBoxes[item] ?: 0) < item.quantity
        }
    }

    // Boolean method to check if there are any quotationItems not fully contained in any boxes
    fun hasQuotationItemsNotInBoxes(): Boolean {
        return findQuotationItemsNotInBoxes().isNotEmpty()
    }

    // Function to find all boxes not fully contained in any containers
    fun findBoxesNotInContainers(): List<BoxOfItem> {
        val boxesInContainers = containers.flatMap { it.boxes }.distinct()
        val boxQuantitiesInContainers = boxesInContainers.groupBy { it }.mapValues { it.value.size }
        return boxes.filter { box -> (boxQuantitiesInContainers[box] ?: 0) < box.boxItems.size
        }
    }

    // Boolean method to check if there are any boxes not fully contained in any containers
    fun hasBoxesNotInContainers(): Boolean {
        return findBoxesNotInContainers().isNotEmpty()
    }
}
