package app.duss.easyproject.presentation.ui.packing.details

import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.presentation.ui.company.CompanyComponent
import app.duss.easyproject.presentation.ui.company.CompanyFilter
import app.duss.easyproject.presentation.ui.item.ItemComponent
import app.duss.easyproject.presentation.ui.packing.details.store.PackingDetailsStore
import app.duss.easyproject.presentation.ui.packing.details.store.PackingDetailsStoreFactory
import app.duss.easyproject.presentation.ui.person.PersonComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializerOrNull

class PackingDetailsComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    id: Long?,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val detailsStore =
        instanceKeeper.getStore {
            PackingDetailsStoreFactory(
                storeFactory = storeFactory,
                id = id,
            ).create()
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
        filter: CompanyFilter,
        (CompanyComponent.Output) -> Unit,
    ) -> CompanyComponent = { childContext, searchValue, filter, output ->
        CompanyComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            filter = filter,
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

    fun itemPickerSelected() {
//        navigation.activate(Configuration.ItemConfig(
//            selected = state.value.item?.customerEnquiryItems?.map { it.item }),
//            onComplete = {
//
//            }
//        )
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
                    CompanyFilter.Customer,
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
//        when (output) {
//            is ItemComponent.Output.ItemsSelected -> onEvent(CEDetailsStore.Intent.EditingState(
//                state.value.item!!.copy(
//                    customerEnquiryItems = output.items.map { item ->
//                        state.value.item?.customerEnquiryItems?.firstOrNull { it.item.id == item.id }
//                            ?: CustomerEnquiryItem(item = item)
//                    }
//                )
//            ))
//        }
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
        data class ItemConfig(val searchValue: String? = "", val selected: List<Item>? = emptyList()) : Configuration()

        @Serializable
        data class CompanyConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class PersonConfig(val searchValue: String? = "") : Configuration()

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<PackingDetailsStore.State> = detailsStore.stateFlow

    fun onEvent(event: PackingDetailsStore.Intent) {
        detailsStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class NavigateBack(
            val deletedId: Long? = null,
            val updatedItem: Packing? = null,
        ) : Output()
    }

}