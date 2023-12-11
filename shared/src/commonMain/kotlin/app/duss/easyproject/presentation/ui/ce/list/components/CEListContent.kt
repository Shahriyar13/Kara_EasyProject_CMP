package app.duss.easyproject.presentation.ui.ce.list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import app.duss.easyproject.domain.entity.CustomerEnquiry
import app.duss.easyproject.presentation.components.PagingVerticalGrid
import app.duss.easyproject.presentation.theme.Blue300
import app.duss.easyproject.presentation.theme.Blue500
import app.duss.easyproject.presentation.theme.Green300
import app.duss.easyproject.presentation.theme.Green500
import app.duss.easyproject.presentation.theme.Indigo300
import app.duss.easyproject.presentation.theme.Indigo500
import app.duss.easyproject.presentation.theme.Orange300
import app.duss.easyproject.presentation.theme.Orange500
import app.duss.easyproject.presentation.theme.Pink300
import app.duss.easyproject.presentation.theme.Pink500
import app.duss.easyproject.presentation.theme.Purple300
import app.duss.easyproject.presentation.theme.Purple500
import app.duss.easyproject.presentation.theme.Red300
import app.duss.easyproject.presentation.theme.Red500
import app.duss.easyproject.presentation.theme.Teal300
import app.duss.easyproject.presentation.theme.Teal500
import app.duss.easyproject.presentation.theme.Yellow300
import app.duss.easyproject.presentation.theme.Yellow500
import app.duss.easyproject.presentation.ui.ce.list.CEListComponent
import app.duss.easyproject.presentation.ui.ce.list.store.CEListStore
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CEListContent(
    state: CEListStore.State,
    onEvent: (CEListStore.Intent) -> Unit,
    onOutput: (CEListComponent.Output) -> Unit,
) {

    val colors = listOf(
        listOf(Green300, Green500),
        listOf(Red300, Red500),
        listOf(Blue300, Blue500),
        listOf(Purple300, Purple500),
        listOf(Orange300, Orange500),
        listOf(Teal300, Teal500),
        listOf(Pink300, Pink500),
        listOf(Indigo300, Indigo500),
        listOf(Yellow300, Yellow500),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Projects") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(onClick = {
                        onEvent(CEListStore.Intent.AddNew)
                    }) {
                        Icon(Icons.Default.Add, "Add New one")
                    }
                }
            )
        },
    ) {
        var index = 0
        PagingVerticalGrid<CustomerEnquiry>(
            modifier = Modifier.padding(it),
            content = { item ->
                index++
                CEItemContents(
                    item = item,
                    brush = remember(index) {
                        val colorIndex = index % colors.size
                        Brush.linearGradient(colors[colorIndex])
                    },
                    onClick = {
                        onEvent(CEListStore.Intent.Details(item.id!!))
                    },
                )
            },
            itemList = state.list,
            isLoading = !state.isLastPageLoaded,
            loadMoreItems = {
                if (state.list.isEmpty()) return@PagingVerticalGrid
                val nextPage = state.page + 1
                onEvent(CEListStore.Intent.LoadListByPage(page = nextPage))
            },
            loadContent = { alpha ->
                CELoadingItem(alpha = alpha)
            }
        )
    }
}
