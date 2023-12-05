package app.duss.easyproject.presentation.ui.project.signlepane

import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStore
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

class ProjectSinglePaneComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    searchValue: String?,
    private val output: (Output) -> Unit,
): ComponentContext by componentContext {

    private val projectList: (
        ComponentContext,
        searchValue: String?,
        (ProjectListComponent.Output) -> Unit,
    ) -> ProjectListComponent = { childContext, searchValue, output ->
        ProjectListComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue,
            output = output
        )
    }

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
            initialConfiguration = Config.List(""),
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
            is Config.List -> Children.List(
                projectList(
                    componentContext,
                    configuration.searchValue,
                    ::onListOutput
                )
            )
        }

    val childStack: Value<ChildStack<*, Children>> = stack

    fun onEvent(event: ProjectListStore.Intent) {
        projectStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }
    sealed class Output {
//        object NavigateBack : Output()
    }

    private fun onListOutput(output: ProjectListComponent.Output) {
        when(output) {
            is ProjectListComponent.Output.NavigateToDetails -> navigation.bringToFront(Config.Details(output.id))
        }
    }

    private fun onDetailsOutput(output: ProjectDetailsComponent.Output) {

    }

    sealed class Config : Parcelable {
        @Serializable
        data class List(val searchValue: String) : Config()

        @Serializable
        data class Details(val id: Long?) : Config()
    }

    sealed class Children {
        data class List(val component: ProjectListComponent) : Children()
        data class Details(val component: ProjectDetailsComponent) : Children()

    }


}