package app.duss.easyproject.presentation.ui.quotation.list

import app.duss.easyproject.presentation.ui.quotation.details.QuotationDetailsComponent
import app.duss.easyproject.presentation.ui.quotation.list.store.QuotationListStore
import app.duss.easyproject.presentation.ui.quotation.list.store.QuotationListStoreFactory
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

class QuotationListComponent(
    componentContext: ComponentContext,
    val storeFactory: StoreFactory,
    val searchValue: String?,
    private val output: (Output) -> Unit,
) : ComponentContext by componentContext {

    private val listStore =
        instanceKeeper.getStore {
            QuotationListStoreFactory(
                storeFactory = storeFactory,
                searchValue = searchValue,
            ).create()
        }

    private fun details(
        componentContext: ComponentContext,
        id: Long?,
    ) = QuotationDetailsComponent(
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
    val state: StateFlow<QuotationListStore.State> = listStore.stateFlow

    fun onEvent(event: QuotationListStore.Intent) {
        listStore.accept(event)
        if (event is QuotationListStore.Intent.Details) {
            navigation.push(Configuration.Details(event.id))
        } else if (event is QuotationListStore.Intent.AddNew) {
            navigation.push(Configuration.Details(null))
        }
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output

    sealed class Child {
        data class Details(val component: QuotationDetailsComponent) : Child()
        data object None : Child()
    }

    sealed class Configuration {
        @Serializable
        data object None : Configuration()
        @Serializable
        data class Details(val id: Long?) : Configuration()

    }


    private fun onDetailsOutput(output: QuotationDetailsComponent.Output) {
        when (output) {
            is QuotationDetailsComponent.Output.NavigateBack -> {
                if (output.deletedId != null) {
                    onEvent(QuotationListStore.Intent.DetailsDeleted(output.deletedId))
                } else if (output.updatedItem != null) {
                    onEvent(QuotationListStore.Intent.DetailsChanged(output.updatedItem))
                } else {
                    onEvent(QuotationListStore.Intent.DetailsDone)
                }
                navigation.pop()
            }
        }
    }
}