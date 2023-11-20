package app.duss.easyproject.ui.main.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.duss.easyproject.ui.main.MainComponent
import app.duss.easyproject.ui.main.state.CategoryState
import app.duss.easyproject.ui.main.store.MainStore

@Composable
internal fun MainContent(
    state: MainStore.State,
    onEvent: (MainStore.Intent) -> Unit,
    onOutput: (MainComponent.Output) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.DatabaseClicked)
            },
            categoryState = CategoryState.ce,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.ProjectClicked)
            },
            categoryState = CategoryState.project,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.SEClicked)
            },
            categoryState = CategoryState.se,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.SQClicked)
            },
            categoryState = CategoryState.sq,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.PIClicked)
            },
            categoryState = CategoryState.pi,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.POClicked)
            },
            categoryState = CategoryState.po,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.ShippingClicked)
            },
            categoryState = CategoryState.shipping,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.InvoiceClicked)
            },
            categoryState = CategoryState.invoice,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.BafaClicked)
            },
            categoryState = CategoryState.bafa,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.PaymentClicked)
            },
            categoryState = CategoryState.payment,
        ),
        CategoryButton(
            onClick = {
                onOutput(MainComponent.Output.ProfileClicked)
            },
            categoryState = CategoryState.profile,
        ),
    )


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),

    ) {

        item(span = { GridItemSpan(2) }) {
            Text(
                text = "What Pokemon are you looking for ?",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            )

            val containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .2f)
            TextField(
                value = state.search,
                onValueChange = { onEvent(MainStore.Intent.InputPokemonSearch(it)) },
                placeholder = {
                    Text(text = "Search Pokemon")
                },
                leadingIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Rounded.Search, contentDescription = "Search Pokemon")
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
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )
        }
            items(
                count = categories.size,
            ) {
                categories[it]
            }
//            item(
//                span = { GridItemSpan(2) }
//            ) {
//                Text("Title")
//            }


//        Text(
//            text = "Watch",
//            color = MaterialTheme.colorScheme.onBackground,
//            style = MaterialTheme.typography.titleLarge.copy(
//                fontWeight = FontWeight.Bold
//            ),
//            modifier = Modifier
//                .padding(horizontal = 20.dp)
//                .padding(top = 20.dp, bottom = 6.dp)
//        )
//
//        Divider(
//            color = MaterialTheme.colorScheme.outline.copy(alpha = .4f),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 20.dp)
//        )
//
//        key(Video.demoList) {
//            VideoRow(
//                videoList = Video.demoList,
//                onVideoClicked = {
//                    onOutput(MainComponent.Output.ComingSoon)
//                }
//            )
//        }

    }
}