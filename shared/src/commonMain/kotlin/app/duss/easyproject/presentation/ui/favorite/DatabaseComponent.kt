package app.duss.easyproject.presentation.ui.favorite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import app.duss.easyproject.presentation.ui.favorite.store.FavoriteStore
import app.duss.easyproject.presentation.ui.favorite.store.FavoriteStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class DatabaseComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val favoriteStore =
        instanceKeeper.getStore {
            FavoriteStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<FavoriteStore.State> = favoriteStore.stateFlow

    fun onEvent(event: FavoriteStore.Intent) {
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