package app.duss.easyproject.presentation.ui.project.pane

import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import app.duss.easyproject.presentation.ui.project.pane.store.ProjectStore
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface ProjectComponent {

    val store: ProjectStore

    val state: StateFlow<ProjectStore.State>

    fun onEvent(event: ProjectStore.Intent) {
        store.accept(event)
    }

    fun onOutput(output: Output)

    val childStack: Value<ChildStack<*, Children>>

    val children: Value<Children>
    fun setMultiPane(isMultiPane: Boolean)

    sealed class Children {
        data class List(val component: ProjectListComponent) : Children()

        data class Details(val component: ProjectDetailsComponent) : Children()

    }

    sealed class Config : Parcelable {
        @Serializable
        data class List(val searchValue: String = "") : Config()

        @Serializable
        data class Details(val id: Long?) : Config()
    }

    sealed class Output {
        data object Unauthorized : Output()

    }
}