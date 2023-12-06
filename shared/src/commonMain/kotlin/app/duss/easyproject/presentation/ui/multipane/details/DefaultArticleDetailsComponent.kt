package app.duss.easyproject.presentation.ui.multipane.details

import app.duss.easyproject.presentation.ui.multipane.details.ArticleDetailsComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultArticleDetailsComponent<T>(
    componentContext: ComponentContext,
    item: T,

    isToolbarVisible: Flow<Boolean>,
    private val onFinished: () -> Unit
) : ArticleDetailsComponent<T>, ComponentContext by componentContext {

    private val _models =
        MutableValue(
            Model(
                isToolbarVisible = true,
                item = item
            )
        )

    override val models: Value<Model<T>> = _models

    init {
        isToolbarVisible.map { isVisible ->
            _models.update { it.copy(isToolbarVisible = isVisible) }
        }
    }

    override fun onCloseClicked() {
        onFinished()
    }
}
