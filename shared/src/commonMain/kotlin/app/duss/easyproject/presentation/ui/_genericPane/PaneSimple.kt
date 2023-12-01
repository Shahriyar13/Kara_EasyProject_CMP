package app.duss.easyproject.presentation.ui._genericPane

import app.duss.easyproject.presentation.ui.project.multipane.store.ProjectStore
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow

class PaneSimple: PaneComponent<ProjectStore, ProjectStore.Intent, ProjectStore.State, Nothing> {
    override val store: ProjectStore
        get() = TODO("Not yet implemented")
    override val state: StateFlow<ProjectStore.State>
        get() = TODO("Not yet implemented")

    override fun onOutput(output: PaneComponent.Output) {
        TODO("Not yet implemented")
    }

    override val childStack: Value<ChildStack<*, PaneComponent.Children>>
        get() = TODO("Not yet implemented")
    override val children: Value<PaneComponent.Children>
        get() = TODO("Not yet implemented")

    override fun setMultiPane(isMultiPane: Boolean) {
        TODO("Not yet implemented")
    }


}