package app.duss.easyproject.presentation.ui.mproject.store

import app.duss.easyproject.domain.entity.Project
import com.arkivanov.mvikotlin.core.store.Store

interface mProjectStore: Store<mProjectStore.Intent, mProjectStore.State, Nothing> {

    sealed class Intent {
        data class LoadProjectListByPage(val page: Int): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
        data object AddNew: Intent()
        data class Details(val id: Long): Intent()
    }

    data class State(
        var page: Int = 0,
        var projectId: Long? = null,
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val projectList: List<Project> = emptyList(),
        val searchValue: String = "",
    )
}