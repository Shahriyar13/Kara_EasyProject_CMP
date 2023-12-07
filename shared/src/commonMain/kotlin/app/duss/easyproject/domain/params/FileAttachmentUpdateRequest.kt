package app.duss.easyproject.domain.params

import com.mohamedrejeb.calf.io.KmpFile

class FileAttachmentUpdateRequest(
    val files: List<KmpFile>,
    val projectId: Long? = null,
    val customerEnquiryId: Long? = null,
    val customerEnquiryItemId: Long? = null,
    val supplierEnquiryId: Long? = null,
    val quotationId: Long? = null,
    val quotationItemId: Long? = null,
    val proformaInvoiceId: Long? = null,
    val purchaseOrderId: Long? = null,
    val shippingId: Long? = null,
    val invoiceId: Long? = null,
    val bafaId: Long? = null,
    val paymentId: Long? = null,
)