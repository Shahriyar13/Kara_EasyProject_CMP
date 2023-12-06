package app.duss.easyproject.presentation.ui.multipane.list

import com.arkivanov.decompose.value.Value

interface ArticleListComponent<T> {

    val models: Value<Model<T>>

    fun onArticleClicked(item: T)

    fun loadNextPage(page: Int)

    data class Model<T>(
        var items: List<T>,
        var page: Int,
        var isLoading: Boolean,
        val isLastPageLoaded: Boolean,
        val item: T? = null
    )
}
