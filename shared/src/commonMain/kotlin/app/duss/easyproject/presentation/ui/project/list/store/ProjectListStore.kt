package app.duss.easyproject.presentation.ui.project.list.store

import app.duss.easyproject.domain.entity.Project
import com.arkivanov.mvikotlin.core.store.Store

interface ProjectListStore: Store<ProjectListStore.Intent, ProjectListStore.State, Nothing> {

    sealed class Intent {
        data class LoadByPage(val page: Int): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
        data object AddNew: Intent()
        data object DetailsDone: Intent()
        data class DetailsChanged(val project: Project): Intent()
        data class DetailsDeleted(val deletedId: Long): Intent()
        data class Edit(val id: Long): Intent()
        data object Refresh: Intent()
    }

    data class State(
        var page: Int = 0,
        var id: Long? = null,
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val list: List<Project> = emptyList(),
        val searchValue: String = "",
    )
}