package app.duss.easyproject.presentation.ui.project.signlepane.store

import app.duss.easyproject.domain.entity.Project
import com.arkivanov.mvikotlin.core.store.Store

interface ProjectListStore: Store<ProjectListStore.Intent, ProjectListStore.State, Nothing> {

    sealed class Intent {
        data class LoadProjectListByPage(val page: Long): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
        data object AddNew: Intent()
        data class Details(val item: Project): Intent()
    }

    data class State(
        var page: Long = 0,
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val projectList: List<Project> = emptyList(),
        val searchValue: String = "",
    )
}