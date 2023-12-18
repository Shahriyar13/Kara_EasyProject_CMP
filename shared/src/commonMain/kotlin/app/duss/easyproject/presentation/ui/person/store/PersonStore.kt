package app.duss.easyproject.presentation.ui.person.store

import app.duss.easyproject.domain.entity.Person
import com.arkivanov.mvikotlin.core.store.Store

interface PersonStore: Store<PersonStore.Intent, PersonStore.State, Nothing> {

    sealed class Intent {
        data class LoadByPage(val page: Int): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
        data object New: Intent()
        data object EditDone: Intent()
        data class Update(val item: Person): Intent()
        data class UpdateSelected(val item: Person): Intent()
        data class Delete(val deletedId: Long): Intent()
        data class Edit(val id: Long): Intent()
        data object Refresh: Intent()
    }

    data class State(
        var page: Int = 0,
        var detail: Person? = null,
        val selectMode: Boolean = false,
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val list: List<Person> = emptyList(),
        val selected: List<Person> = emptyList(),
        val selectingDone: Boolean= false,
        val searchValue: String = "",
    )

}