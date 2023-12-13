package app.duss.easyproject.presentation.ui.project.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.components.EditableText
import app.duss.easyproject.presentation.components.TopAppBarDocumentDetails
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.details.store.ProjectDetailsStore
import kotlinx.coroutines.launch

@Composable
internal fun ProjectDetailsContent(
    state: ProjectDetailsStore.State,
    onEvent: (ProjectDetailsStore.Intent) -> Unit,
    onOutput: (ProjectDetailsComponent.Output) -> Unit,
    modifier: Modifier = Modifier.padding(LocalSafeArea.current),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(contentAlignment = Alignment.TopCenter) {

        if (state.isDeleted) {
            onOutput(ProjectDetailsComponent.Output.NavigateBack(state.projectForm!!.origin.id))
        }

        state.projectForm?.let { item ->

        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBarDocumentDetails(
                    onBack = {
                        onOutput(
                            ProjectDetailsComponent.Output.NavigateBack(
                                updatedProject = if (state.isUpdated) state.projectForm?.origin else null,
                            )
                        )
                    },
                    onSave = {
                        if (!state.isLoading && state.projectForm != null)
                            onEvent(ProjectDetailsStore.Intent.SaveState(state.projectForm, state.isChanged))
                    },
                    onEdit = {
                        onEvent(ProjectDetailsStore.Intent.EditState)
                    },
                    editState = state.inEditeMode,
                    showUnsavedChanges = state.isChanged,
                    title = state.projectForm?.updated?.code,
                    loadingState = state.isLoading
                )
            },
            containerColor = Color.Transparent,
            modifier = modifier
        ) { paddingValue ->
            Box(
                modifier = Modifier
                    .padding(paddingValue)
                    .padding(20.dp)
            ) {
                if (state.isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.error?.let { error ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            error,
                            withDismissAction = true,
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }

                state.projectForm?.let { item ->
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item("name") {
                            EditableText(
                                value = item.updated.code.replaceFirstChar { it.uppercaseChar() },
                                onValueChange = {
                                    state.projectForm.updated.code = it ?: ""
                                    onEvent(ProjectDetailsStore.Intent.EditingState)
                                },
                                label = "Code",
                                readOnly = !state.inEditeMode,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item("title") {
                            EditableText(
                                value = item.origin.title,
                                onValueChange = {
                                    state.projectForm.updated.title = it ?: ""
                                    onEvent(ProjectDetailsStore.Intent.EditingState)
                                },
                                label = "Title",
                                readOnly = !state.inEditeMode,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

//                        item("abilities") {
//                            AbilityRow(
//                                types = pokemonInfo.types
//                            )
//                        }

                        item("infos") {
                            PokemonInfos(
                                project = item.origin,
                                modifier = Modifier
                                    .padding(top = 18.dp)
                                    .fillMaxWidth(.9f)
                            )
                        }

                        item("stats") {
                            PokemonStats(
                                project = item.origin,
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .fillMaxWidth(.9f)
                            )
                        }
                    }
                }
            }
        }
    }
}