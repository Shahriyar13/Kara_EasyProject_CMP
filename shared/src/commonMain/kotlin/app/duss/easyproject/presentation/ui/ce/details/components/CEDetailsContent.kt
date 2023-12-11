package app.duss.easyproject.presentation.ui.ce.details.components

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
import app.duss.easyproject.presentation.components.TopAppBarDocument
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.ce.details.CEDetailsComponent
import app.duss.easyproject.presentation.ui.ce.details.store.CEDetailsStore
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.details.store.ProjectDetailsStore
import kotlinx.coroutines.launch

@Composable
internal fun CEDetailsContent(
    state: CEDetailsStore.State,
    onEvent: (CEDetailsStore.Intent) -> Unit,
    onOutput: (CEDetailsComponent.Output) -> Unit,
    modifier: Modifier = Modifier.padding(LocalSafeArea.current),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(contentAlignment = Alignment.TopCenter) {

        if (state.isDeleted) {
            onOutput(CEDetailsComponent.Output.NavigateBack(state.form!!.origin.id))
        }

        state.form?.let { item ->

        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBarDocument(
                    onBack = {
                        onOutput(
                            CEDetailsComponent.Output.NavigateBack(
                                updatedItem = if (state.isUpdated) state.form?.origin else null,
                            )
                        )
                    },
                    onSave = {
                        if (!state.isLoading && state.form != null)
                            onEvent(CEDetailsStore.Intent.SaveState(state.form, state.isChanged))
                    },
                    onEdit = {
                        onEvent(CEDetailsStore.Intent.EditState)
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
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item("name") {
                            EditableText(
                                value = item.updated.code.replaceFirstChar { it.uppercaseChar() },
                                onValueChange = {
                                    state.form.updated.code = it ?: ""
                                    onEvent(CEDetailsStore.Intent.EditingState)
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
                                    state.form.updated.title = it ?: ""
                                    onEvent(CEDetailsStore.Intent.EditingState)
                                },
                                label = "Title",
                                readOnly = !state.inEditeMode,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                    }
                }
            }
        }
    }
}