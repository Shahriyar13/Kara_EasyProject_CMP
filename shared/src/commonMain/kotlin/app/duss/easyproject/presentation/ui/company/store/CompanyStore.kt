package app.duss.easyproject.presentation.ui.company.store

import app.duss.easyproject.domain.entity.Project
import com.arkivanov.mvikotlin.core.store.Store

interface CompanyStore: Store<CompanyStore.Intent, CompanyStore.State, Nothing> {

    sealed class Intent

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val list: List<Project> = emptyList(),
    )


}