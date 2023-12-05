package app.duss.easyproject.presentation.ui.project.multipane

import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStore
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

class ProjectMultiPaneComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    searchValue: String?,
    private val output: (Output) -> Unit,
): ComponentContext by componentContext {

    private val projectDetails: (
        ComponentContext, id: Long?,
        (ProjectDetailsComponent.Output) -> Unit,
    ) -> ProjectDetailsComponent = { childContext, id, output ->
        ProjectDetailsComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            id = id,
            output = output
        )
    }

    private val projectStore =
        instanceKeeper.getStore {
            ProjectListStoreFactory(
                storeFactory = storeFactory,
                searchValue = searchValue,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ProjectListStore.State> = projectStore.stateFlow

    private val navigation = StackNavigation<Config>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Config.None,
            handleBackButton = false,
            childFactory = ::createChild
        )

    private fun createChild(
        configuration: Config,
        componentContext: ComponentContext
    ): Children =
        when (configuration) {
            is Config.Details -> Children.Details(
                projectDetails(
                    componentContext,
                    configuration.id,
                    ::onDetailsOutput
                )
            )
            is Config.None -> Children.None
        }

    val childStack: Value<ChildStack<*, Children>> = stack

    fun onEvent(event: ProjectListStore.Intent) {
        projectStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    fun onNewItemClicked() {
        navigation.push(Config.Details(null))
    }

    fun onItemClicked(id: Long) {
        navigation.push(Config.Details(id))
    }

    sealed class Output {

    }


    private fun onDetailsOutput(output: ProjectDetailsComponent.Output) {

    }

    sealed class Config : Parcelable {
        @Serializable
        data object None : Config()

        @Serializable
        data class Details(val id: Long?) : Config()
    }

    sealed class Children {
        data object None : Children()
        data class Details(val component: ProjectDetailsComponent) : Children()

    }

}