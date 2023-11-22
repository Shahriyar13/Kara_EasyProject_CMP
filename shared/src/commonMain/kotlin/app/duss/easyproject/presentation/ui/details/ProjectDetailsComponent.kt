package app.duss.easyproject.presentation.ui.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import app.duss.easyproject.presentation.ui.details.store.DetailsStore
import app.duss.easyproject.presentation.ui.details.store.DetailsStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class ProjectDetailsComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    id: Long?,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val detailsStore =
        instanceKeeper.getStore {
            DetailsStoreFactory(
                storeFactory = storeFactory,
                pokemonName = id?.toString() ?: ""
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<DetailsStore.State> = detailsStore.stateFlow

    fun onEvent(event: DetailsStore.Intent) {
        detailsStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data object NavigateBack : Output()
    }

}