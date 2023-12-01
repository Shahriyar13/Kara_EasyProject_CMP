package app.duss.easyproject.presentation.ui.project.list

import app.duss.easyproject.presentation.ui.project.multipane.store.ProjectListStore
import app.duss.easyproject.presentation.ui.project.multipane.store.ProjectListStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class ProjectListComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    searchValue: String?,
    private val output: (Output) -> Unit,
): ComponentContext by componentContext {

    private val projectStore =
        instanceKeeper.getStore {
            ProjectListStoreFactory(
                storeFactory = storeFactory,
                searchValue = searchValue,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ProjectListStore.State> = projectStore.stateFlow

    fun onEvent(event: ProjectListStore.Intent) {
        projectStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }
    sealed class Output {
//        object NavigateBack : Output()
//        data class NavigateToDetails(val id: Long?) : Output()
    }


}