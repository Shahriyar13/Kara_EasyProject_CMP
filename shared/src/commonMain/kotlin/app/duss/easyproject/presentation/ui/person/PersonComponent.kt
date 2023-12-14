package app.duss.easyproject.presentation.ui.person

import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.presentation.ui.person.store.PersonStore
import app.duss.easyproject.presentation.ui.person.store.PersonStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class PersonComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    val selectMode: Boolean = false,
    val searchValue: String?,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {

    private val favoriteStore =
        instanceKeeper.getStore {
            PersonStoreFactory(
                storeFactory = storeFactory,
                searchValue = searchValue,
                selectMode = selectMode,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<PersonStore.State> = favoriteStore.stateFlow

    fun onEvent(event: PersonStore.Intent) {
        favoriteStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class ItemsSelected(val items: List<Person>) : Output()

    }

}