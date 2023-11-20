package app.duss.easyproject.ui.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import app.duss.easyproject.ui.main.store.MainStore
import app.duss.easyproject.ui.main.store.MainStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class DashboardComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val mainStore =
        instanceKeeper.getStore {
            MainStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MainStore.State> = mainStore.stateFlow

    fun onEvent(event: MainStore.Intent) {
        mainStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class SearchSubmitted(val searchValue: String) : Output()
//        data object MainClicked : Output()
        data object DatabaseClicked : Output()
//        data object CEClicked : Output()
        data object ProjectClicked : Output()
//        data object SEClicked : Output()
//        data object SQClicked : Output()
//        data object PIClicked : Output()
//        data object POClicked : Output()
//        data object ShippingClicked : Output()
//        data object InvoiceClicked : Output()
//        data object BafaClicked : Output()
//        data object PaymentClicked : Output()
//        data object ProfileClicked : Output()
        data object ComingSoonClicked : Output()
    }

}