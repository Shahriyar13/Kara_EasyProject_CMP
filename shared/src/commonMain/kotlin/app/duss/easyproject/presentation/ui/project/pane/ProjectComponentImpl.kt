package app.duss.easyproject.presentation.ui.project.pane

import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import app.duss.easyproject.presentation.ui.project.pane.store.ProjectStore
import app.duss.easyproject.presentation.ui.project.pane.store.ProjectStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class ProjectComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (ProjectComponent.Output) -> Unit,

    private val projectList: (ComponentContext, searchValue: String, (ProjectListComponent.Output) -> Unit) -> ProjectListComponent = { childContext, searchValue, output ->
        ProjectListComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue,
            output = output
        )
    },
    private val projectDetails: (ComponentContext, id: Long?, (ProjectDetailsComponent.Output) -> Unit) -> ProjectDetailsComponent = { childContext, id, output ->
        ProjectDetailsComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            id = id,
            output = output
        )
    },
) : ProjectComponent, ComponentContext by componentContext {

    override val store =
        instanceKeeper.getStore {
            ProjectStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<ProjectStore.State> = store.stateFlow

    override fun onOutput(output: ProjectComponent.Output) {
        output(output)
    }

    private val navigation = StackNavigation<ProjectComponent.Config>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = ProjectComponent.Config.List(),
            handleBackButton = false,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, ProjectComponent.Children>> = stack

    private fun createChild(
        configuration: ProjectComponent.Config,
        componentContext: ComponentContext
    ): ProjectComponent.Children =
        when (configuration) {
            is ProjectComponent.Config.Details -> ProjectComponent.Children.Details(
                projectDetails(
                    componentContext,
                    configuration.id,
                    ::onDetailsOutput
                )
            )
            is ProjectComponent.Config.List -> ProjectComponent.Children.List(
                projectList(
                    componentContext,
                    configuration.searchValue,
                    ::onListOutput
                )
            )
        }

    private fun onDetailsOutput(output: ProjectDetailsComponent.Output) {

    }

    private fun onListOutput(output: ProjectListComponent.Output) {


    }

}