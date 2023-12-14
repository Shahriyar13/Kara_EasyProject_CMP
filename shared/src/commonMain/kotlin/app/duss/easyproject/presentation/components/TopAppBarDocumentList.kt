package app.duss.easyproject.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TrailingIconState {
    DELETE,
    CLOSE
}

enum class SearchAppBarState {
    CLOSED,
    OPENED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDocumentList(
    title: String?,
    loadingState: Boolean = false,
    onSearchValueChange: ((String) -> Unit)? = null,
    onAddClicked: (() -> Unit)? = null,
    searchValue: String? = null,
    actions: @Composable RowScope.() -> Unit = {},
    bottomContent: (@Composable () -> Unit)? = null,
) {
    Column {
        var searchValueState by remember { mutableStateOf(searchValue ?: "") }

        var searchAppBarState by remember { mutableStateOf(SearchAppBarState.CLOSED) }
        var trailingIconState by remember { mutableStateOf(TrailingIconState.CLOSE) }

        TopAppBar(
            title = {
                if (searchAppBarState == SearchAppBarState.OPENED) {
                    val containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .2f)
                    TextField(
                        value = searchValueState,
                        singleLine = true,
                        onValueChange = {
                            searchValueState = it
                            trailingIconState = if (it.isEmpty()) {
                                 TrailingIconState.CLOSE
                            } else {
                                onSearchValueChange?.invoke(it)
                                TrailingIconState.DELETE
                            }
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp
                        ),
                        placeholder = {
                            Text(text = if (title.isNullOrEmpty()) "Search" else "Search for $title")
                        },
                        leadingIcon = {
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(Icons.Rounded.Search, contentDescription = "Search")
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                when (trailingIconState) {
                                    TrailingIconState.DELETE -> {
                                        if (searchValueState.isNotEmpty()) {
                                            searchValueState = ""
                                            onSearchValueChange?.invoke("")
                                        }
                                        trailingIconState = TrailingIconState.CLOSE
                                    }
                                    TrailingIconState.CLOSE -> {
                                        if (searchValueState.isNotEmpty()) {
                                            searchValueState = ""
                                            onSearchValueChange?.invoke("")
                                        } else {
                                            searchAppBarState = SearchAppBarState.CLOSED
                                            trailingIconState = TrailingIconState.DELETE
                                        }
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close",
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedLeadingIconColor = MaterialTheme.colorScheme.surface,
                            unfocusedLeadingIconColor = MaterialTheme.colorScheme.surface,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                onSearchValueChange?.invoke(searchValueState)
                            }
                        ),
                        shape = MaterialTheme.shapes.extraLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    )
                } else {
                    title?.let { Text(it) }
                }
            },
            actions = {
                if (onSearchValueChange != null && searchAppBarState == SearchAppBarState.CLOSED) {
                    IconButton(
                        onClick = {
                            searchAppBarState = SearchAppBarState.OPENED
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "search_icon",
                        )
                    }
                }
                if (onAddClicked != null) {
                    IconButton(onClick = {
                        onAddClicked()
                    }) {
                        Icon(Icons.Default.Add, "Add New")
                    }
                }
                actions()
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
        bottomContent?.let { content ->
            content()
        }
        if (loadingState) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(4.dp))
        } else {
            Spacer(modifier = Modifier.fillMaxWidth().height(4.dp))
        }
    }
}
