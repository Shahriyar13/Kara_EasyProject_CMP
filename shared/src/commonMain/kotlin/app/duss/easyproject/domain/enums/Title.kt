package app.duss.easyproject.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class Title(val id: Int) {
    Unknown(0),
    Mr(1),
    Ms(2),
    Mrs(3),
    Dr(4);
}