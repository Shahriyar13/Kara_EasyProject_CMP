package app.duss.easyproject.presentation.ui.item

import app.duss.easyproject.domain.entity.Item
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
    val selectMode: Boolean = false,
    val searchValue: String?,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            ItemStoreFactory(
                storeFactory = storeFactory,
                searchValue = searchValue,
                selectMode = selectMode,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ItemStore.State> = store.stateFlow

    fun onEvent(event: ItemStore.Intent) {
        store.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class ItemsSelected(val items: List<Item>) : Output()

    }

}