package app.duss.easyproject.presentation.ui.multipane.list

import app.duss.easyproject.presentation.ui.multipane.list.ArticleListComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultArticleListComponent<T>(
    componentContext: ComponentContext,
    val getItems: (Int) -> List<T>,
    selectedItem: Flow<T?>,
    private val onArticleSelected: (item: T) -> Unit
) : ArticleListComponent<T>, ComponentContext by componentContext {


    private val _models =
        MutableValue(
            Model(
                items = listOf<T>(),
                item = null,
                isLastPageLoaded = false,
                isLoading = false,
                page = 0,
            )
        )

    override val models: Value<Model<T>> = _models

    init {
        selectedItem.map { item ->
            _models.update { it.copy(item = item) }
        }
    }

    override fun onArticleClicked(item: T) {
        onArticleSelected(item)
    }

    override fun loadNextPage(page: Int) {
        _models.update {
            it.copy(items = it.items + getItems(page))
        }
    }
}
