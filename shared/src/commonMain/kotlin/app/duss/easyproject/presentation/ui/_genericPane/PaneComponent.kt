package app.duss.easyproject.presentation.ui._genericPane

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface PaneComponent<STORE: Store<Intent, State, Label>, Intent: Any, State: Any, Label: Any> {

    val store: STORE

    val state: StateFlow<State>

    fun onEvent(event: Intent) {
        store.accept(event)
    }

    fun onOutput(output: Output)

    val childStack: Value<ChildStack<*, Children>>

    val children: Value<Children>
    fun setMultiPane(isMultiPane: Boolean)

    sealed class Children {
        data class List(val component: ComponentContext) : Children()

        data class Details(val component: ComponentContext) : Children()

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