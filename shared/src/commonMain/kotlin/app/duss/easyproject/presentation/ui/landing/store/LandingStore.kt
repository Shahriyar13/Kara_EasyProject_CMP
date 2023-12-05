package app.duss.easyproject.presentation.ui.landing.store

import app.duss.easyproject.domain.entity.User
import com.arkivanov.mvikotlin.core.store.Store

interface LandingStore: Store<LandingStore.Intent, LandingStore.State, Nothing> {

    sealed class Intent {

        data object LoggedInUserLoading : Intent()

        data class LoggedInUserLoaded(val user: User): Intent()

        data object LoggedInUserFailed : Intent()

    }

    data class State(
        val isLoading: Boolean = true,
        val error: String? = null,
        val username: String? = null,
        val password: String? = null,
        val user: User? = null,
    )

}