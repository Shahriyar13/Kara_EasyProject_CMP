package app.duss.easyproject.presentation.ui.multipane

import app.duss.easyproject.presentation.ui.multipane.details.ArticleDetailsComponent
import app.duss.easyproject.presentation.ui.multipane.list.ArticleListComponent
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.value.Value

interface MultiPaneComponent<T> {

    val children: Value<Children>

    fun onItemClicked(item: T)

    fun setMultiPane(isMultiPane: Boolean)

    data class Children(
        val isMultiPane: Boolean,
        val listChild: Child.Created<*, ArticleListComponent<*>>,
        val detailsChild: Child.Created<*, ArticleDetailsComponent<*>>?,
    )
}
