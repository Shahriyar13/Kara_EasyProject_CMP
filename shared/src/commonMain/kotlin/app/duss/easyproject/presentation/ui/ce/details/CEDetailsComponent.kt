package app.duss.easyproject.presentation.ui.ce.details

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.presentation.ui.ce.details.store.CEDetailsStore
import app.duss.easyproject.presentation.ui.ce.details.store.CEDetailsStoreFactory
import app.duss.easyproject.presentation.ui.company.CompanyComponent
import app.duss.easyproject.presentation.ui.item.ItemComponent
import app.duss.easyproject.presentation.ui.person.PersonComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializerOrNull

class CEDetailsComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    id: Long?,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val detailsStore =
        instanceKeeper.getStore {
            CEDetailsStoreFactory(
                storeFactory = storeFactory,
                id = id,
            ).create()
        }

    val itemComponent = ItemComponent(
        componentContext,
        storeFactory,
        true,
        "",
        ::onItemOutput
    )

    val item: (
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

    private val navigation = SlotNavigation<Configuration>()

    @OptIn(InternalSerializationApi::class)
    private val childSlot =
        childSlot(
            source = navigation,
            serializer = Configuration::class.serializerOrNull(),
            key = "nav",
            childFactory = ::createChild
        )

    fun off() {
        navigation.activate(Configuration.ItemConfig(), onComplete = {})
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

//    private val topNavigation = SlotNavigation<TopConfig>()
//
//    private val topSlot =
//        childSlot<TopConfig, TopChild>(
//            source = topNavigation,
//            key = "TopSlot",
//            // Omitted code
//        )
//
//    private val bottomNavigation = SlotNavigation<BottomConfig>()
//
//    private val bottomSlot =
//        childSlot<BottomConfig, BottomChild>(
//            source = bottomNavigation,
//            key = "BottomSlot",
//            // Omitted code
//        )


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

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<CEDetailsStore.State> = detailsStore.stateFlow

    fun onEvent(event: CEDetailsStore.Intent) {
        detailsStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class NavigateBack(
            val deletedId: Long? = null,
            val updatedItem: CustomerEnquiry? = null,
        ) : Output()
    }

}