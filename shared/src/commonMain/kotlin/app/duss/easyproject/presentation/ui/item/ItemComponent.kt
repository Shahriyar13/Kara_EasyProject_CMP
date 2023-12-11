package app.duss.easyproject.presentation.ui.item

import app.duss.easyproject.presentation.ui.item.store.ItemStore
import app.duss.easyproject.presentation.ui.item.store.ItemStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class ItemComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    val searchValue: String?,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val favoriteStore =
        instanceKeeper.getStore {
            ItemStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ItemStore.State> = favoriteStore.stateFlow

    fun onEvent(event: ItemStore.Intent) {
        favoriteStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class NavigateToCustomerDetails(val id: Long?) : Output()
        data class NavigateToSupplierDetails(val id: Long?) : Output()
        data class NavigateToItemDetails(val id: Long?) : Output()
        data class NavigateToPersonDetails(val id: Long?) : Output()
    }

}