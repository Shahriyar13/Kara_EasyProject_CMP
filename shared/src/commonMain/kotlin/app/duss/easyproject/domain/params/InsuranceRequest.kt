package app.duss.easyproject.domain.params

import kotlinx.serialization.Serializable

@Serializable
data class InsuranceRequest(
    val id: Long? = null,
    val name: String?,
)
