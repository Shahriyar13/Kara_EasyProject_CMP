package app.duss.easyproject.presentation.ui.database


import app.duss.easyproject.presentation.ui.company.CompanyComponent
import app.duss.easyproject.presentation.ui.item.ItemComponent
import app.duss.easyproject.presentation.ui.person.PersonComponent
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
        searchValue: String?,
        (CompanyComponent.Output) -> Unit,
    ) -> CompanyComponent = { childContext, searchValue, output ->
        CompanyComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue ?: "",
            output = output
        )
    }

    private val person: (
        ComponentContext,
        searchValue: String?,
        (PersonComponent.Output) -> Unit,
    ) -> PersonComponent = { childContext, searchValue, output ->
        PersonComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue ?: "",
            output = output
        )
    }


    private val navigation = StackNavigation<Configuration>()

    @OptIn(InternalSerializationApi::class)
    private val stack =
        childStack(
            source = navigation,
            serializer = Configuration::class.serializerOrNull(),
            initialConfiguration = Configuration.CompanyConfig(null),
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

    sealed class Output

    fun onItemTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.ItemConfig(searchValue))
    }

    fun onCompanyTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.CompanyConfig(searchValue))
    }

    fun onPersonTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.PersonConfig(searchValue))
    }

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.ItemConfig -> Child.ItemChild(
                item(
                    componentContext,
                    null,
                    ::onItemOutput
                )
            )
            is Configuration.CompanyConfig -> Child.CompanyChild(
                company(
                    componentContext,
                    null,
                    ::onCompanyOutput
                )
            )
            is Configuration.PersonConfig -> Child.PersonChild(
                person(
                    componentContext,
                    null,
                    ::onPersonOutput
                )
            )
        }

    private fun onItemOutput(output: ItemComponent.Output) {

    }

    private fun onCompanyOutput(output: CompanyComponent.Output) {

    }

    private fun onPersonOutput(output: PersonComponent.Output) {

    }

    sealed class Child {
        data class ItemChild(val component: ItemComponent) : Child()

        data class CompanyChild(val component: CompanyComponent) : Child()

        data class PersonChild(val component: PersonComponent) : Child()

    }


    sealed class Configuration {
        @Serializable
        data class ItemConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class CompanyConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class PersonConfig(val searchValue: String? = "") : Configuration()

    }

}