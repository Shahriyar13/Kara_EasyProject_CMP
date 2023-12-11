package app.duss.easyproject.presentation.ui.ce.list

import app.duss.easyproject.presentation.ui.ce.details.CEDetailsComponent
import app.duss.easyproject.presentation.ui.ce.list.store.CEListStore
import app.duss.easyproject.presentation.ui.ce.list.store.CEListStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializerOrNull

class CEListComponent(
    componentContext: ComponentContext,
    val storeFactory: StoreFactory,
    val searchValue: String?,
    private val output: (Output) -> Unit,
) : ComponentContext by componentContext {

    private val listStore =
        instanceKeeper.getStore {
            CEListStoreFactory(
                storeFactory = storeFactory,
                searchValue = searchValue,
            ).create()
        }

    private fun details(
        componentContext: ComponentContext,
        id: Long?,
    ) = CEDetailsComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            id = id,
            output = ::onDetailsOutput
        )


    private val navigation = StackNavigation<Configuration>()

    @OptIn(InternalSerializationApi::class)
    private val stack =
        childStack(
            source = navigation,
            serializer = Configuration::class.serializerOrNull(),
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
                details(
                    componentContext,
                    configuration.id,
                )
            )

            Configuration.None -> Child.None
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<CEListStore.State> = listStore.stateFlow

    fun onEvent(event: CEListStore.Intent) {
        listStore.accept(event)
        if (event is CEListStore.Intent.Details) {
            navigation.push(Configuration.Details(event.id))
        } else if (event is CEListStore.Intent.AddNew) {
            navigation.push(Configuration.Details(null))
        }
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output

    sealed class Child {
        data class Details(val component: CEDetailsComponent) : Child()
        data object None : Child()
    }

    sealed class Configuration {
        @Serializable
        data object None : Configuration()
        @Serializable
        data class Details(val id: Long?) : Configuration()

    }


    private fun onDetailsOutput(output: CEDetailsComponent.Output) {
        when (output) {
            is CEDetailsComponent.Output.NavigateBack -> {
                if (output.deletedId != null) {
                    onEvent(CEListStore.Intent.DetailsDeleted(output.deletedId))
                } else if (output.updatedItem != null) {
                    onEvent(CEListStore.Intent.DetailsChanged(output.updatedItem))
                } else {
                    onEvent(CEListStore.Intent.DetailsDone)
                }
                navigation.pop()
            }
        }
    }
}