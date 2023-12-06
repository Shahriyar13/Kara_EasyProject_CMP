package app.duss.easyproject.presentation.ui.dashboard.store

import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.utils.appDispatchers
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.koin.core.component.KoinComponent

internal class DashboardStoreFactory(
    private val storeFactory: StoreFactory,
): KoinComponent {
    fun create(): DashboardStore =
        object : DashboardStore, Store<DashboardStore.Intent, DashboardStore.State, Nothing> by storeFactory.create(
            name = "DashboardStore",
            initialState = DashboardStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class PokemonSearchChanged(val search: String) : Msg()
        data object UserLoading : Msg()
        data class UserLoaded(val user: User) : Msg()
        data object UserFailed : Msg()

    }

    private inner class ExecutorImpl : CoroutineExecutor<DashboardStore.Intent, Unit, DashboardStore.State, Msg, Nothing>(
        appDispatchers.main) {

        override fun executeIntent(intent: DashboardStore.Intent, getState: () -> DashboardStore.State): Unit =
            when (intent) {

                is DashboardStore.Intent.InputPokemonSearch -> dispatch(Msg.PokemonSearchChanged(intent.search))
            }
    }

    private object ReducerImpl: Reducer<DashboardStore.State, Msg> {
        override fun DashboardStore.State.reduce(msg: Msg): DashboardStore.State =
            when (msg) {
                is Msg.PokemonSearchChanged -> copy(search = msg.search)
                Msg.UserFailed -> TODO()
                is Msg.UserLoaded -> TODO()
                Msg.UserLoading -> TODO()
            }
    }

}