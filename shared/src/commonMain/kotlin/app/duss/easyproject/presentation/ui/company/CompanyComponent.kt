package app.duss.easyproject.presentation.ui.company

import app.duss.easyproject.presentation.ui.company.store.CompanyStore
import app.duss.easyproject.presentation.ui.company.store.CompanyStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class CompanyComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val favoriteStore =
        instanceKeeper.getStore {
            CompanyStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<CompanyStore.State> = favoriteStore.stateFlow

    fun onEvent(event: CompanyStore.Intent) {
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