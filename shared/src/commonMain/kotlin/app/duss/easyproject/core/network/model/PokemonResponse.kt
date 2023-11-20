package app.duss.easyproject.core.network.model

import app.duss.easyproject.core.model.Project
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
  val count: Int,
  val next: String?,
  val previous: String?,
  val results: List<Project>
)
