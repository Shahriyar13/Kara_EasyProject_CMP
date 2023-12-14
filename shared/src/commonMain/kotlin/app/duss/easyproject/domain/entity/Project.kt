package app.duss.easyproject.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    var title: String?,
    val customerEnquiries: List<CustomerEnquiry>? = listOf(),
    val supplierEnquiries: List<SupplierEnquiry>? = listOf(),
    val quotations: List<Quotation>? = listOf(),
    val proformaInvoices: List<ProformaInvoice>? = listOf(),
    val purchaseOrders: List<PurchaseOrder>? = listOf(),
    val packings: List<Packing>? = listOf(),
    val invoices: List<Invoice>? = listOf(),
    //TODO BAFAs
    //TODO Payments
    val managers: List<Person>? = listOf(),
    override val sendTime: Long? = null,
    override var code: String,
    override val codeExtension: String? = null,
    override val time: Long,
    override val annualId: Int,
    override val id: Long? = null,
    override val creationTime: Long = 0,
    override val modificationTime: Long? = null,
    override val createdBy: String = "",
    override val modifiedBy: String? = null,
    override val creatorId: Long? = null,
    override val modifierId: Long? = null
): BaseDocumentEntity()