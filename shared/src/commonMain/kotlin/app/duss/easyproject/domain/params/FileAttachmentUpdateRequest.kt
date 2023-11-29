package app.duss.easyproject.domain.params

import com.darkrockstudios.libraries.mpfilepicker.MPFile

class FileAttachmentUpdateRequest(
    val file: MPFile<Any>,
    val projectId: Long?,
    val customerEnquiryId: Long?,
    val customerEnquiryItemId: Long?,
    val supplierEnquiryId: Long?,
    val quotationId: Long?,
    val quotationItemId: Long?,
    val proformaInvoiceId: Long?,
    val purchaseOrderId: Long?,
)