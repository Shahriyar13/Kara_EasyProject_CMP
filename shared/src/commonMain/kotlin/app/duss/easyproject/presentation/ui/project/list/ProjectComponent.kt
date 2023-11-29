package app.duss.easyproject.presentation.ui.project.list

import app.duss.easyproject.presentation.ui.project.list.store.ProjectStore
import app.duss.easyproject.presentation.ui.project.list.store.ProjectStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class ProjectComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    searchValue: String,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val projectStore =
        instanceKeeper.getStore {
            ProjectStoreFactory(
                storeFactory = storeFactory,
                searchValue = searchValue,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ProjectStore.State> = projectStore.stateFlow

    fun onEvent(event: ProjectStore.Intent) {
        projectStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
//        object NavigateBack : Output()
        data class NavigateToDetails(val id: Long?) : Output()
    }

}