package app.duss.easyproject.presentation.ui.project.pane.store

import app.duss.easyproject.utils.appDispatchers
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.koin.core.component.KoinComponent

class ProjectStoreFactory (
    private val storeFactory: StoreFactory,
): KoinComponent {
    fun create(): ProjectStore =
        object : ProjectStore, Store<ProjectStore.Intent, ProjectStore.State, Nothing> by storeFactory.create(
            name = ProjectStore::class.simpleName,
            initialState = ProjectStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ProjectStoreFactory::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    sealed class Msg {
        data object New : Msg()
        data class Details(val id: Long) : Msg()
        data object List : Msg()
    }
    private inner class ExecutorImpl : CoroutineExecutor<ProjectStore.Intent, Unit, ProjectStore.State, Msg, Nothing>(appDispatchers.main) {
        override fun executeAction(action: Unit, getState: () -> ProjectStore.State) {
        }

        override fun executeIntent(intent: ProjectStore.Intent, getState: () -> ProjectStore.State): Unit =
            when (intent) {
                ProjectStore.Intent.NewSelected -> dispatch(Msg.New)
                is ProjectStore.Intent.DetailsSelected -> dispatch(Msg.Details(intent.id))
                ProjectStore.Intent.DetailsEditDone -> dispatch(Msg.List)
            }
    }

    internal object ReducerImpl: Reducer<ProjectStore.State, Msg> {
        override fun ProjectStore.State.reduce(msg: Msg): ProjectStore.State =
            when (msg) {
                is Msg.New -> copy(itemId = -1)
                is Msg.Details -> ProjectStore.State(itemId = msg.id)
                Msg.List -> copy(itemId = null)
            }
    }
}