package app.duss.easyproject.ui.project.store

import app.duss.easyproject.domain.entity.Project
import com.arkivanov.mvikotlin.core.store.Store

interface ProjectStore: Store<ProjectStore.Intent, ProjectStore.State, Nothing> {

    sealed class Intent {
        data class LoadProjectListByPage(val page: Long): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val projectList: List<Project> = emptyList(),
        val searchValue: String = "",
    )
}