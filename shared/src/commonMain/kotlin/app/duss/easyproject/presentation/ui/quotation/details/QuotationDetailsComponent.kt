package app.duss.easyproject.presentation.ui.quotation.details

import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.domain.entity.Quotation
import app.duss.easyproject.presentation.ui.company.CompanyComponent
import app.duss.easyproject.presentation.ui.item.ItemComponent
import app.duss.easyproject.presentation.ui.person.PersonComponent
import app.duss.easyproject.presentation.ui.quotation.details.store.QuotationDetailsStore
import app.duss.easyproject.presentation.ui.quotation.details.store.QuotationDetailsStoreFactory
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

class QuotationDetailsComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    id: Long?,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val detailsStore =
        instanceKeeper.getStore {
            QuotationDetailsStoreFactory(
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

    fun itemPickerSelected() {
//        navigation.activate(Configuration.ItemConfig(
//            selected = state.value.item?.quotationItems?.map { it.customerEnquiryItem }),
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
//            is ItemComponent.Output.ItemsSelected -> onEvent(QuotationDetailsStore.Intent.EditingState(
//                state.value.item!!.copy(
//                    quotationItems = output.items.map { customerEnquiryItem ->
//                        state.value.item?.quotationItems?.firstOrNull { it.customerEnquiryItem.id == customerEnquiryItem.id }
//                            ?: QuotationItem(customerEnquiryItem = customerEnquiryItem)
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
    val state: StateFlow<QuotationDetailsStore.State> = detailsStore.stateFlow

    fun onEvent(event: QuotationDetailsStore.Intent) {
        detailsStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class NavigateBack(
            val deletedId: Long? = null,
            val updatedItem: Quotation? = null,
        ) : Output()
    }

}