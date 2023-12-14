package app.duss.easyproject.presentation.forms

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.domain.params.toDto

data class CustomerEnquiryForm(
    override val origin: CustomerEnquiry,
    override val updated: CustomerEnquiry = origin.copy(),
): BaseForm() {
    override fun toRequestDto() = updated.toDto()
}