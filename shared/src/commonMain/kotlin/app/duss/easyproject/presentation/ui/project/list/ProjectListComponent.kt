package app.duss.easyproject.presentation.ui.project.list

import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStore
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

class ProjectListComponent(
    componentContext: ComponentContext,
    val storeFactory: StoreFactory,
    val searchValue: String?,
    private val output: (Output) -> Unit,
) : ComponentContext by componentContext {

    private val projectListStore =
        instanceKeeper.getStore {
            ProjectListStoreFactory(
                storeFactory = storeFactory,
                searchValue = searchValue,
            ).create()
        }

    private fun projectDetails(
        componentContext: ComponentContext,
        id: Long?,
    ) = ProjectDetailsComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            id = id,
            output = ::onDetailsOutput
        )


    private val navigation = StackNavigation<Configuration>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.None,
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            is Configuration.Details -> Child.Details(
                projectDetails(
                    componentContext,
                    configuration.id,
                )
            )

            Configuration.None -> Child.None
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ProjectListStore.State> = projectListStore.stateFlow

    fun onEvent(event: ProjectListStore.Intent) {
        projectListStore.accept(event)
        if (event is ProjectListStore.Intent.Details) {
            navigation.push(Configuration.Details(event.id))
        } else if (event is ProjectListStore.Intent.AddNew) {
            navigation.push(Configuration.Details(null))
        }
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output

    sealed class Child {
        data class Details(val component: ProjectDetailsComponent) : Child()
        data object None : Child()
    }

    sealed class Configuration : Parcelable {
        @Serializable
        data object None : Configuration()
        @Serializable
        data class Details(val id: Long?) : Configuration()

    }


    private fun onDetailsOutput(output: ProjectDetailsComponent.Output) {
        when (output) {
            ProjectDetailsComponent.Output.NavigateBack -> {
                onEvent(ProjectListStore.Intent.DetailsDone)
                navigation.pop()
            }
        }
    }
}