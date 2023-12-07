package app.duss.easyproject.presentation.ui.landing.store

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

class LandingStoreFactory(
    private val storeFactory: StoreFactory,
) : KoinComponent {

    private val loggedInUserUseCase by inject<UserLoggedInUseCase>()

    fun create(): LandingStore =
        object : LandingStore,
            Store<LandingStore.Intent, LandingStore.State, Nothing> by storeFactory.create(
                name = LandingStore::class.simpleName,
                initialState = LandingStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data object Loading : Msg()
        data class Loaded(val user: User) : Msg()
        data class Failed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<LandingStore.Intent, Unit, LandingStore.State, Msg, Nothing>(
            appDispatchers.main
        ) {
        override fun executeAction(action: Unit, getState: () -> LandingStore.State) {
            getLoggedInUser()
        }

        override fun executeIntent(
            intent: LandingStore.Intent,
            getState: () -> LandingStore.State
        ) {
            when (intent) {
                LandingStore.Intent.LoggedInUserFailed -> dispatch(Msg.Failed("Not Logged in"))
                is LandingStore.Intent.LoggedInUserLoaded -> dispatch(Msg.Loaded(intent.user))
                LandingStore.Intent.LoggedInUserLoading -> dispatch(Msg.Loading)
            }
        }

        private var job: Job? = null
        private fun getLoggedInUser() {
            if (job?.isActive == true) return

            job = scope.launch {
                dispatch(Msg.Loading)

                loggedInUserUseCase
                    .execute(null)
                    .onSuccess { user ->
                        dispatch(Msg.Loaded(user))
                    }
                    .onFailure { e ->
                        dispatch(Msg.Failed(e.message))
                    }
            }
        }
    }

    private object ReducerImpl : Reducer<LandingStore.State, Msg> {
        override fun LandingStore.State.reduce(msg: Msg): LandingStore.State =
            when (msg) {
                is Msg.Loading -> copy(isLoading = true)
                is Msg.Loaded -> LandingStore.State(user = msg.user)
                is Msg.Failed -> copy(error = msg.error)
            }
    }
}