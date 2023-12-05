package app.duss.easyproject.presentation.ui.landing

import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.presentation.ui.landing.store.LandingStore
import app.duss.easyproject.presentation.ui.landing.store.LandingStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class LandingComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val favoriteStore =
        instanceKeeper.getStore {
            LandingStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<LandingStore.State> = favoriteStore.stateFlow

    fun onEvent(event: LandingStore.Intent) {
        favoriteStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class Authorized(val user: User?) : Output()
        data object Unauthorized : Output()

    }

}