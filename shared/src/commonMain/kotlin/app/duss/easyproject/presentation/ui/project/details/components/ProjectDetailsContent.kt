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
            onOutput(ProjectDetailsComponent.Output.NavigateBack(state.form!!.origin.id))
        }

        state.form?.let { item ->

        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBarDocumentDetails(
                    onBack = {
                        onOutput(
                            ProjectDetailsComponent.Output.NavigateBack(
                                updatedProject = if (state.isUpdated) state.form?.origin else null,
                            )
                        )
                    },
                    onSave = {
                        if (!state.isLoading && state.form != null)
                            onEvent(ProjectDetailsStore.Intent.SaveState(state.form, state.isChanged))
                    },
                    onEdit = {
                        onEvent(ProjectDetailsStore.Intent.EditState)
                    },
                    editState = state.inEditeMode,
                    showUnsavedChanges = state.isChanged,
                    title = state.form?.updated?.code,
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

                state.form?.let { item ->

                    val projectState = item.updated

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item("name") {
                            EditableText(
                                value = projectState.code,
                                onValueChange = {
                                    onEvent(ProjectDetailsStore.Intent.EditingState(projectState.copy(code = it ?: "" )))
                                },
                                label = "Code",
                                readOnly = !state.inEditeMode,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item("title") {
                            EditableText(
                                value = projectState.title,
                                onValueChange = {
                                    onEvent(ProjectDetailsStore.Intent.EditingState(projectState.copy(title = it ?: "" )))
                                },
                                label = "Title",
                                readOnly = !state.inEditeMode,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

//                        item("abilities") {//TODO Code extention for metpool
//                            AbilityRow(
//                                types = pokemonInfo.types
//                            )
//                        }

                        item("managers") {
                            AbilityRow(
                                managers = projectState.managers ?: listOf()
                            )
                        }

                        item("infos") {
                            PokemonInfos(
                                project = projectState,
                                modifier = Modifier
                                    .padding(top = 18.dp)
                                    .fillMaxWidth(.9f)
                            )
                        }

                        item("stats") {
                            PokemonStats(
                                project = projectState,
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