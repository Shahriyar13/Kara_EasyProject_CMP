package app.duss.easyproject.presentation.ui.item.store

import app.duss.easyproject.domain.entity.Project
import com.arkivanov.mvikotlin.core.store.Store

interface ItemStore: Store<ItemStore.Intent, ItemStore.State, Nothing> {

    sealed class Intent

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val projectList: List<Project> = emptyList(),
    )

}