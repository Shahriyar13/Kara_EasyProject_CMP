package app.duss.easyproject.presentation.ui.project.details

import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.presentation.ui.project.details.store.ProjectDetailsStore
import app.duss.easyproject.presentation.ui.project.details.store.ProjectDetailsStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class ProjectDetailsComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    id: Long?,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val detailsStore =
        instanceKeeper.getStore {
            ProjectDetailsStoreFactory(
                storeFactory = storeFactory,
                projectId = id,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ProjectDetailsStore.State> = detailsStore.stateFlow

    fun onEvent(event: ProjectDetailsStore.Intent) {
        detailsStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class NavigateBack(
            val deletedId: Long? = null,
            val updatedProject: Project? = null,
        ) : Output()
    }

}