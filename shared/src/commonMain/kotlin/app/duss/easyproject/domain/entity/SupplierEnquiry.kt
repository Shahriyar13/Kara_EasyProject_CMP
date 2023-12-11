package app.duss.easyproject.domain.entity

data class SupplierEnquiry(
    val project: Project,
    val supplier: Company,
    val customerEnquiryItems: List<CustomerEnquiryItem> = ArrayList(),
    val fileAttachments: List<FileAttachment> = ArrayList(),
)
