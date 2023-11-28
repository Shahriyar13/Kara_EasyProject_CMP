package app.duss.easyproject.domain.params

import com.darkrockstudios.libraries.mpfilepicker.MPFile

class CustomerEnquiryItemCreateOrUpdateRequest (
    val id: Long?,
    val customerEnquiryId: Long,
    val itemId: Long?,
    val item: ItemCreateRequest?,
    var quantity: Int,
    var note: String? = null,
    val fileAttachments: List<MPFile<Any>>? = ArrayList(),
)