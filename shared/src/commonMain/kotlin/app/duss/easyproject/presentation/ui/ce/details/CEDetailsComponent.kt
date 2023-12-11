package app.duss.easyproject.presentation.ui.ce.details

import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.presentation.ui.ce.details.store.CEDetailsStore
import app.duss.easyproject.presentation.ui.ce.details.store.CEDetailsStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

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