package app.duss.easyproject.presentation.ui.landing.store

import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.params.UserLoginRequest
import app.duss.easyproject.domain.usecase.UserLoginUseCase
import app.duss.easyproject.utils.appDispatchers
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
): KoinComponent {

    private val userLoginUseCase by inject<UserLoginUseCase>()

    fun create(): LandingStore =
        object : LandingStore, Store<LandingStore.Intent, LandingStore.State, Nothing> by storeFactory.create(
            name = LandingStore::class.simpleName,
            initialState = LandingStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class UsernameValueUpdated(val username: String?) : Msg()
        data class PasswordValueUpdated(val password: String?) : Msg()
        data object Loading : Msg()
        data class Loaded(val user: User) : Msg()
        data class Failed(val error: String?) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<LandingStore.Intent, Unit, LandingStore.State, Msg, Nothing>(appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> LandingStore.State) {

        }

        override fun executeIntent(intent: LandingStore.Intent, getState: () -> LandingStore.State): Unit =
            when (intent) {
                LandingStore.Intent.LoginButtonPressed -> loginUser(getState().username, getState().password)
                is LandingStore.Intent.UpdateUsernameValue -> dispatch(Msg.UsernameValueUpdated(intent.username))
                is LandingStore.Intent.UpdatePasswordValue -> dispatch(Msg.PasswordValueUpdated(intent.password))
            }

        private var job: Job? = null
        private fun loginUser(username: String?, password: String?) {
            if (job?.isActive == true) return

            if (username.isNullOrEmpty()) return dispatch(Msg.Failed("Enter Your Username"))
            if (password.isNullOrEmpty()) return dispatch(Msg.Failed("Enter Your Password"))

            job = scope.launch {
                dispatch(Msg.Loading)

                userLoginUseCase
                    .execute(UserLoginRequest(username, password))
                    .onSuccess {
                        it?.let {
                            dispatch(Msg.Loaded(it))
                        } ?: dispatch(Msg.Failed("Unknown error"))
                    }.onFailure {
                        dispatch(Msg.Failed(it.message))
                    }
            }
        }
    }

    private object ReducerImpl: Reducer<LandingStore.State, Msg> {
        override fun LandingStore.State.reduce(msg: Msg): LandingStore.State =
            when (msg) {
                is Msg.UsernameValueUpdated -> copy(username = msg.username)
                is Msg.PasswordValueUpdated -> copy(password = msg.password)
                is Msg.Loading -> copy(isLoading = true)
                is Msg.Loaded -> LandingStore.State(user = msg.user)
                is Msg.Failed -> copy(error = msg.error)
            }
    }
}