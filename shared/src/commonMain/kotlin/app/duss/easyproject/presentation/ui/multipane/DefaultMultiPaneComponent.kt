package app.duss.easyproject.presentation.ui.multipane

import app.duss.easyproject.domain.entity.BaseEntity
import app.duss.easyproject.presentation.ui.multipane.MultiPaneComponent.Children
import app.duss.easyproject.presentation.ui.multipane.details.ArticleDetailsComponent
import app.duss.easyproject.presentation.ui.multipane.details.DefaultArticleDetailsComponent
import app.duss.easyproject.presentation.ui.multipane.list.ArticleListComponent
import app.duss.easyproject.presentation.ui.multipane.list.DefaultArticleListComponent
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.ChildNavState
import com.arkivanov.decompose.router.children.ChildNavState.Status
import com.arkivanov.decompose.router.children.NavState
import com.arkivanov.decompose.router.children.SimpleChildNavState
import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

class DefaultMultiPaneComponent<T: BaseEntity>(
    title: String,
    tSerializer: KSerializer<T>,
    val getItems: (page: Int) -> List<T>,
    componentContext: ComponentContext,
) : MultiPaneComponent<T>, ComponentContext by componentContext {

    private val navigation = SimpleNavigation<(NavigationState<T>) -> NavigationState<T>>()
    private val navState = MutableStateFlow<NavigationState<T>?>(null).asStateFlow()

    override val children: Value<Children> =
        children(
            source = navigation,
            stateSerializer = NavigationState.serializer(tSerializer),
            key = title,
            initialState = ::NavigationState,
            navTransformer = { navState, event -> event(navState) },
            stateMapper = { navState, children ->
                Children(
                    isMultiPane = navState.isMultiPane,
                    listChild = children.first { it.instance is ArticleListComponent<*> } as Child.Created<*, ArticleListComponent<T>>,
                    detailsChild = children.find { it.instance is ArticleDetailsComponent<*> } as Child.Created<*, ArticleDetailsComponent<T>>?,
                )
            },
            onStateChanged = { newState, _ -> navState.value?.copy(isMultiPane = newState.isMultiPane)},
            childFactory = ::child,
        )

    override fun onItemClicked(item: T) {

    }

    private fun child(config: Config, componentContext: ComponentContext): Any =
        when (config) {
            is Config.List -> listComponent(componentContext)
            is Config.Details<*> -> detailsComponent(config, componentContext)
        }

    private fun listComponent(componentContext: ComponentContext): ArticleListComponent<T> =
        DefaultArticleListComponent(
            componentContext = componentContext,
            getItems = { getItems(it) },
            selectedItem = navState.map { if (it!!.isMultiPane) it.item else null },
        ) { item -> navigation.navigate { it.copy(item = item) } }

    private fun <T> detailsComponent(config: Config.Details<T>, componentContext: ComponentContext): ArticleDetailsComponent<T> =
        DefaultArticleDetailsComponent(
            componentContext = componentContext,
            item = config.item,
            isToolbarVisible = navState.map { !it!!.isMultiPane },
        ) { navigation.navigate { it.copy(item = null) } }

    override fun setMultiPane(isMultiPane: Boolean) {
        navigation.navigate { it.copy(isMultiPane = isMultiPane) }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List: Config

        @Serializable
        data class Details<T>(val item: T) : Config
    }

    @Serializable
    private data class NavigationState<T: BaseEntity>(
        val isMultiPane: Boolean = false,
        val item: T? = null,
    ) : NavState<Config> {
        override val children: List<ChildNavState<Config>> by lazy {
            listOfNotNull(
                SimpleChildNavState(Config.List, if (isMultiPane || (item == null)) Status.ACTIVE else Status.INACTIVE),
                if (item != null) SimpleChildNavState(Config.Details(item), Status.ACTIVE) else null,
            )
        }
    }
}
