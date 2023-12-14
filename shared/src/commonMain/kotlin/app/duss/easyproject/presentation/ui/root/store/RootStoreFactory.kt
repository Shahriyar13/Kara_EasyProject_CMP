package app.duss.easyproject.presentation.ui.root.store

import app.duss.easyproject.core.utils.appDispatchers
import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.usecase.auth.UserLoggedInUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class RootStoreFactory(
    private val storeFactory: StoreFactory,
): KoinComponent {

    private val loggedInUserUseCase by inject<UserLoggedInUseCase>()

    fun create(): RootStore =
        object : RootStore, Store<RootStore.Intent, RootStore.State, Nothing> by storeFactory.create(
            name = RootStore::class.simpleName,
            initialState = RootStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    sealed class Msg {
        data object LoggedInUserLoading : Msg()
        data class LoggedInUserLoaded(val user: User) : Msg()
        data class LoggedInUserFailed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<RootStore.Intent, Unit, RootStore.State, Msg, Nothing>(
        appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> RootStore.State) {
            getLoggedInUser()
        }

        override fun executeIntent(intent: RootStore.Intent, getState: () -> RootStore.State): Unit =
            when (intent) {
                RootStore.Intent.LoggedInUserFailed -> dispatch(Msg.LoggedInUserFailed("Not Logged in"))
                is RootStore.Intent.LoggedInUserLoaded -> dispatch(Msg.LoggedInUserLoaded(intent.user))
                RootStore.Intent.LoggedInUserLoading -> dispatch(Msg.LoggedInUserLoading)
            }

        private var job: Job? = null
        private fun getLoggedInUser() {
            if (job?.isActive == true) return

            job = scope.launch {
                dispatch(Msg.LoggedInUserLoading)

                loggedInUserUseCase
                    .execute(null)
                    .onSuccess { user ->
                        dispatch(Msg.LoggedInUserLoaded(user))
                    }
                    .onFailure { e ->
                        dispatch(Msg.LoggedInUserFailed(e.message))
                    }
            }
        }
    }

    internal object ReducerImpl: Reducer<RootStore.State, Msg> {
        override fun RootStore.State.reduce(msg: Msg): RootStore.State =
            when (msg) {
                is Msg.LoggedInUserFailed -> copy(error = msg.error)
                is Msg.LoggedInUserLoaded -> RootStore.State(user = msg.user)
                Msg.LoggedInUserLoading -> copy(isLoading = true)
            }
    }
}