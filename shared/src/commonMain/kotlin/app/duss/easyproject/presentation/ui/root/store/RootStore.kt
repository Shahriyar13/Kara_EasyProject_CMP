package app.duss.easyproject.presentation.ui.root.store

import app.duss.easyproject.domain.entity.User
import com.arkivanov.mvikotlin.core.store.Store

interface RootStore: Store<RootStore.Intent, RootStore.State, Nothing> {

    sealed class Intent {
        data object LoggedInUserLoading : Intent()

        data class LoggedInUserLoaded(val user: User): Intent()

        data object LoggedInUserFailed : Intent()
    }

    data class State(
        var user: User? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}