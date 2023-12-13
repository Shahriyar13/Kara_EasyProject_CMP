package app.duss.easyproject.presentation.ui.person.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.components.SimpleListItemContent
import app.duss.easyproject.presentation.components.SimplePagingVerticalGrid
import app.duss.easyproject.presentation.components.TopAppBarDocumentList
import app.duss.easyproject.presentation.ui.person.PersonComponent
import app.duss.easyproject.presentation.ui.person.store.PersonStore
import kotlinx.coroutines.launch

@Composable
internal fun PersonContent(
    state: PersonStore.State,
    onEvent: (PersonStore.Intent) -> Unit,
    onOutput: (PersonComponent.Output) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBarDocumentList(
                title = "Persons",
                onSearchValueChange = {
                    onEvent(PersonStore.Intent.UpdateSearchValue(it))
                },
                searchValue = state.searchValue,
                loadingState = state.isLoading,
                onAddClicked = {
                    //TODO()
                },
            )
        },
    ) { paddingValue ->
        state.error?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    error,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
        SimplePagingVerticalGrid(
            itemList = state.list,
            loadMoreItems = {
                if (state.list.isEmpty()) return@SimplePagingVerticalGrid
                val nextPage = state.page + 1
                onEvent(PersonStore.Intent.LoadByPage(page = nextPage))
            },
            isLoading = state.isLoading,
            modifier = Modifier.padding(paddingValue)
        ) { item, brush ->
            SimpleListItemContent(
                onClick = {
                    if (state.selectMode) {
                        onEvent(PersonStore.Intent.UpdateSelected(item = item))
                    } else {
                        item.id?.let {
                            onEvent(PersonStore.Intent.Edit(id = item.id))
                        }
                    }
                },
                title = item.fullName(),
                subtitle = item.jobTitle,
                brush = brush
            )
        }
    }
}
