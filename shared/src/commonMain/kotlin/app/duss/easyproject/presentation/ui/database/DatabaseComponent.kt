package app.duss.easyproject.presentation.ui.database


import app.duss.easyproject.presentation.ui.item.ItemComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializerOrNull

class DatabaseComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    fun onOutput(output: Output) {
        output(output)
    }

    private val item: (
        ComponentContext,
        searchValue: String?,
        (ItemComponent.Output) -> Unit,
    ) -> ItemComponent = { childContext, searchValue, output ->
        ItemComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue ?: "",
            output = output
        )
    }

    private val company: (
        ComponentContext,
        (CompanyComponent.Output) -> Unit,
    ) -> CompanyComponent = { childContext, output ->
        CompanyComponent(
            componentContext = childContext,
            output = output
        )
    }

    private val person: (
        ComponentContext,
        (PersonComponent.Output) -> Unit,
    ) -> PersonComponent = { childContext, output ->
        PersonComponent(
            componentContext = childContext,
            output = output
        )
    }


    private val navigation = StackNavigation<Configuration>()

    @OptIn(InternalSerializationApi::class)
    private val stack =
        childStack(
            source = navigation,
            serializer = Configuration::class.serializerOrNull(),
            initialConfiguration = Configuration.Item,
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

    sealed class Output

    fun onItemTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.Item(searchValue))
    }

    fun onCompanyTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.Company(searchValue))
    }

    fun onPersonTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.Person(searchValue))
    }

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Item -> Child.Item(
                item(
                    componentContext,
                    ::onItemOutput
                )
            )
            is Configuration.Company -> Child.Company(
                company(
                    componentContext,
                    ::onCompanyOutput
                )
            )
            is Configuration.Person -> Child.Person(
                person(
                    componentContext,
                    ::onPersonOutput
                )
            )
        }

    sealed class Child {
        data class Item(val component: ItemComponent) : Child()

        data class Company(val component: CompanyComponent) : Child()

        data class Person(val component: PersonComponent) : Child()

    }


    sealed class Configuration {
        @Serializable
        data class Item(val searchValue: String? = "") : Configuration()

        @Serializable
        data class Company(val searchValue: String? = "") : Configuration()

        @Serializable
        data class Person(val searchValue: String? = "") : Configuration()

    }

}