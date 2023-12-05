package app.duss.easyproject.presentation.ui.project.store

import app.duss.easyproject.utils.appDispatchers
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.koin.core.component.KoinComponent

internal class ProjectStoreFactory(
    private val storeFactory: StoreFactory,
): KoinComponent {
    fun create(): ProjectStore =
        object : ProjectStore, Store<ProjectStore.Intent, ProjectStore.State, Nothing> by storeFactory.create(
            name = ProjectStore::class.simpleName,
            initialState = ProjectStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data object SinglePane : Msg()
        data object MultiPane : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<ProjectStore.Intent, Unit, ProjectStore.State, Msg, Nothing>(appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> ProjectStore.State) {
        }

        override fun executeIntent(intent: ProjectStore.Intent, getState: () -> ProjectStore.State): Unit =
            when (intent) {
                ProjectStore.Intent.MultiPane -> dispatch(Msg.MultiPane)
                ProjectStore.Intent.SinglePane -> dispatch(Msg.SinglePane)
            }
        }

    private object ReducerImpl: Reducer<ProjectStore.State, Msg> {
        override fun ProjectStore.State.reduce(msg: Msg): ProjectStore.State =
            when (msg) {
                Msg.SinglePane -> copy(isMultiPane = false)
                Msg.MultiPane -> copy(isMultiPane = true)
            }
    }
}