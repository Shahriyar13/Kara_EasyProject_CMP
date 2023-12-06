package app.duss.easyproject.presentation.ui.mproject

import app.duss.easyproject.presentation.ui.mproject.store.mProjectStore
import app.duss.easyproject.presentation.ui.mproject.store.mProjectStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class mProjectComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    searchValue: String?,
    private val output: (Output) -> Unit,
): ComponentContext by componentContext {

    private val projectListStore =
        instanceKeeper.getStore {
            mProjectStoreFactory(
                storeFactory = storeFactory,
                searchValue = searchValue,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<mProjectStore.State> = projectListStore.stateFlow

    fun onEvent(event: mProjectStore.Intent) {
        projectListStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }
    sealed class Output {
        data class NavigateToDetails(val id: Long?) : Output()
    }

}