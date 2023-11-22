package app.duss.easyproject.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse<out T>(
  val code: Int,
  val friendlyMessage: String? = null,
  val errorMessage: String? = null,
  val data: T? = null,
  val paging: Paging? = null,
) {
  @Serializable
  data class Paging(
    val isFirstPage: Boolean,
    val isLastPage: Boolean,
    val pageNumber: Int,
    val lastPageNumber: Int,
    val totalElements: Long,
  ) {
    @Suppress("unused")
    val isSinglePage: Boolean = isFirstPage && isLastPage
  }
}