package app.duss.easyproject.domain.params

import app.duss.easyproject.domain.entity.Insurance
import kotlinx.serialization.Serializable

@Serializable
data class InsuranceRequest(
    val id: Long? = null,
    val name: String?,
)

fun Insurance.toDto() = InsuranceRequest(
    id = id,
    name = name,
)