package app.duss.easyproject.presentation.ui.multipane.details

import com.arkivanov.decompose.value.Value

interface ArticleDetailsComponent<T> {

    val models: Value<Model<T>>

    fun onCloseClicked()

    data class Model<T>(
        val isToolbarVisible: Boolean,
        val item: T,
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}
