package app.duss.easyproject.domain.params

import com.darkrockstudios.libraries.mpfilepicker.MPFile

class CustomerEnquiryCreateRequest (
    val title: String?,
    val code: String?,
    val codeExtension: String?,
    val annualId: Int?,
    val customerBuyerId: Long,
    val customerConsigneeId: Long?,
    val customerEndUserId: Long?,
    val projectId: Long?,
    val time: Long,
    val customerEnquiryItems: List<CustomerEnquiryItemCreateOrUpdateRequest>? = ArrayList(),
    val fileAttachments: List<MPFile<Any>>? = ArrayList(),
)
