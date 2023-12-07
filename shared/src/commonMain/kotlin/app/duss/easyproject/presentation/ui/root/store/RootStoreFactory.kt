package app.duss.easyproject.presentation.ui.root.store

import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.usecase.auth.UserLoggedInUseCase
import app.duss.easyproject.presentation.ui.main.store.MainStore
import app.duss.easyproject.core.utils.appDispatchers
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class RootStoreFactory(
    private val storeFactory: StoreFactory,
): KoinComponent {

    private val loggedInUserUseCase by inject<UserLoggedInUseCase>()

    fun create(): MainStore =
        object : MainStore, Store<MainStore.Intent, MainStore.State, Nothing> by storeFactory.create(
            name = "RootStore",
            initialState = MainStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    sealed class Msg {
        data object LoggedInUserLoading : Msg()
        data class LoggedInUserLoaded(val user: User) : Msg()
        data class LoggedInUserFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<MainStore.Intent, Unit, MainStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> MainStore.State) {
        }

        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State): Unit =
            when (intent) {
                MainStore.Intent.LoggedInUserFailed -> dispatch(Msg.LoggedInUserFailed("Not Logged in"))
                is MainStore.Intent.LoggedInUserLoaded -> dispatch(Msg.LoggedInUserLoaded(intent.user))
                MainStore.Intent.LoggedInUserLoading -> dispatch(Msg.LoggedInUserLoading)
            }
    }

    internal object ReducerImpl: Reducer<MainStore.State, Msg> {
        override fun MainStore.State.reduce(msg: Msg): MainStore.State =
            when (msg) {
                is Msg.LoggedInUserFailed -> copy(error = msg.error)
                is Msg.LoggedInUserLoaded -> MainStore.State(user = msg.user)
                Msg.LoggedInUserLoading -> copy(isLoading = true)
            }
    }
}