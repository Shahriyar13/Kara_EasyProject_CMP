package app.duss.easyproject.presentation.ui.login.store

import app.duss.easyproject.domain.entity.User
import com.arkivanov.mvikotlin.core.store.Store

interface LoginStore: Store<LoginStore.Intent, LoginStore.State, Nothing> {

    sealed class Intent {
        data class UpdateUsernameValue(val username: String): Intent()

        data class UpdatePasswordValue(val password: String): Intent()

        data object LoginButtonPressed: Intent()

    }

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val username: String? = null,
        val password: String? = null,
        val user: User? = null,
    )

}