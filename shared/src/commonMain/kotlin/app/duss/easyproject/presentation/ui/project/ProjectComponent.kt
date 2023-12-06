package app.duss.easyproject.presentation.ui.project

import app.duss.easyproject.presentation.ui.project.multipane.ProjectMultiPaneComponent
import app.duss.easyproject.presentation.ui.project.signlepane.ProjectSinglePaneComponent
import app.duss.easyproject.presentation.ui.project.store.ProjectStore
import app.duss.easyproject.presentation.ui.project.store.ProjectStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

class ProjectComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    searchValue: String,
    private val output: (Output) -> Unit,
): ComponentContext by componentContext {

    private val projectSinglePane: (
        ComponentContext, searchValue: String?,
        (ProjectSinglePaneComponent.Output) -> Unit,
    ) -> ProjectSinglePaneComponent = { childContext, searchValue,  output ->
        ProjectSinglePaneComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue,
            output = output
        )
    }

    private val projectMultiPane: (
        ComponentContext, searchValue: String?,
        (ProjectMultiPaneComponent.Output) -> Unit,
    ) -> ProjectMultiPaneComponent = { childContext, searchValue, output ->
        ProjectMultiPaneComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue,
            output = output
        )
    }

    private val projectStore =
        instanceKeeper.getStore {
            ProjectStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }


    private val navigation = StackNavigation<Config>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Config.SinglePane(""),
            handleBackButton = false,
            childFactory = ::createChild
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ProjectStore.State> = projectStore.stateFlow

    private fun createChild(
        configuration: Config,
        componentContext: ComponentContext
    ): Children =
        when (configuration) {
            is Config.SinglePane -> Children.SinglePane(
                projectSinglePane(
                    componentContext,
                    configuration.searchValue,
                    ::onSinglePaneOutput
                )
            )
            is Config.MultiPane -> Children.MultiPane(
                projectMultiPane(
                    componentContext,
                    configuration.searchValue,
                    ::onMultiPaneOutput
                )
            )
        }

    private fun onMultiPaneOutput(output: ProjectMultiPaneComponent.Output) {

    }

    private fun onSinglePaneOutput(output: ProjectSinglePaneComponent.Output) {

    }

    val childStack: Value<ChildStack<*, Children>> = stack

    fun onOutput(output: Output) {
        output(output)
    }
    sealed class Output

    fun onEvent(event: ProjectStore.Intent) {
        projectStore.accept(event)
    }

    sealed class Config : Parcelable {
        @Serializable
        data class SinglePane(val searchValue: String) : Config()

        @Serializable
        data class MultiPane(val searchValue: String) : Config()
    }

    sealed class Children {
        data class SinglePane(val component: ProjectSinglePaneComponent) : Children()
        data class MultiPane(val component: ProjectMultiPaneComponent) : Children()

    }
}