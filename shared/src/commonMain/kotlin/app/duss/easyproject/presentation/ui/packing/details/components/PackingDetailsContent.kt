package app.duss.easyproject.presentation.ui.packing.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.components.EditableText
import app.duss.easyproject.presentation.components.TopAppBarDocumentDetails
import app.duss.easyproject.presentation.components.draganddrop.DragAndDropState
import app.duss.easyproject.presentation.components.draganddrop.DragAndDropSurface
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.packing.details.PackingDetailsComponent
import app.duss.easyproject.presentation.ui.packing.details.store.PackingDetailsStore
import kotlinx.coroutines.launch

@Composable
internal fun PackingDetailsContent(
    state: PackingDetailsStore.State,
    onEvent: (PackingDetailsStore.Intent) -> Unit,
    onOutput: (PackingDetailsComponent.Output) -> Unit,
    modifier: Modifier = Modifier.padding(LocalSafeArea.current),
    component: PackingDetailsComponent,
) {

    val boardState = remember { DragAndDropState(true) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val itemState by remember { mutableStateOf(state.item) }

    val quotationItemsNotInBoxes by remember { mutableStateOf(itemState?.quotationItemsNotInBoxes) }
    val boxes by remember { mutableStateOf(itemState?.boxes) }

    Box(contentAlignment = Alignment.TopCenter) {

        if (state.isDeleted) {
            onOutput(PackingDetailsComponent.Output.NavigateBack(state.item!!.id))
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBarDocumentDetails(
                    onBack = {
                        onOutput(
                            PackingDetailsComponent.Output.NavigateBack(
                                updatedItem = if (state.isUpdated) state.item else null,
                            )
                        )
                    },
                    onSave = {
                        if (!state.isLoading && state.item != null)
                            onEvent(
                                PackingDetailsStore.Intent.SaveState(
                                    state.item,
                                    state.isChanged
                                )
                            )
                    },
                    onEdit = {
                        onEvent(PackingDetailsStore.Intent.EditState)
                    },
                    editState = state.inEditeMode,
                    showUnsavedChanges = state.isChanged,
                    title = if ((state.item?.id ?: -1) > 0) "Packing: " + (state.item?.code
                        ?: "") else "New Packing",
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

                LaunchedEffect(key1 = boardState.movingCardData) {
                    if (boardState.hasCardMoved()) {
                        onEvent(
                            PackingDetailsStore.Intent.MoveQuotationItemToBox(
                                quotationItemId = boardState.movingCardData.first,
                                oldBoxId = boardState.cardDraggedInitialListId,
                                newBoxId = boardState.movingCardData.second
                            )
                        )
//                        viewModel.moveCardToDifferentList(
//                            cardId = boardState.movingCardData.first,
//                            oldListId = boardState.cardDraggedInitialListId,
//                            newListId = boardState.movingCardData.second
//                        )
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

                state.item?.let { item ->
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item("name") {
                            EditableText(
                                value = item.code?.replaceFirstChar { it.uppercaseChar() },
                                onValueChange = {
                                    onEvent(
                                        PackingDetailsStore.Intent.EditingState(
                                            item.copy(
                                                code = it ?: ""
                                            )
                                        )
                                    )
                                },
                                label = "Code",
                                readOnly = !state.inEditeMode,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

//                        item("title") {
//                            EditableText(
//                                value = item.title,
//                                onValueChange = {
//                                    onEvent(
//                                        PackingDetailsStore.Intent.EditingState(
//                                            item.copy(
//                                                title = it ?: ""
//                                            )
//                                        )
//                                    )
//
//                                },
//                                label = "Title",
//                                readOnly = !state.inEditeMode,
//                                modifier = Modifier.fillMaxWidth()
//                            )
//                        }

                        item("items") {

//                            Row(
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                modifier = Modifier.fillMaxWidth(),
//                            ) {
//                                Text(
//                                    "List of Packs:",
//                                    modifier
//                                        .padding(top = 26.dp, bottom = 16.dp)
//                                )
//                                if (state.inEditeMode) {
//                                    Button(
//                                        onClick = {
//                                            component.itemPickerSelected()
//                                        }
//                                    ) {
//                                        Icon(Icons.Default.List, "List")
//                                        Text("Open Item List")
//                                    }
//                                }
//                            }

                            DragAndDropSurface(
                                state = boardState,
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    QuotationItemColumn(
                                        modifier = Modifier.weight(1F),
                                        list = quotationItemsNotInBoxes ?: listOf(),
                                        editState = state.inEditeMode,
                                        onItemUpdate = { updatedItem ->

                                        },
                                        onItemDelete = { deletedItem ->

                                        }
                                    )

                                    BoxColumn(
                                        boardState = boardState,
                                        modifier = Modifier.weight(2F),
                                        list = boxes ?: listOf(),
                                        editState = state.inEditeMode,
                                        onItemUpdate = { updatedItem ->
//                                            itemState?.boxes = itemState!!.boxes.map {
//                                                if (updatedItem.uniqueId == it.uniqueId) {
//                                                    updatedItem
//                                                } else {
//                                                    it
//                                                }
//                                            }
                                            onEvent(PackingDetailsStore.Intent.EditingState(
                                                itemState!!
                                            ))
                                        },
                                        onItemDelete = { deletedItem ->

                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}