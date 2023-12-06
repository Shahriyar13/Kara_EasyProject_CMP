package app.duss.easyproject.presentation.ui.mproject.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.multipane.details.ArticleDetailsComponent
import app.duss.easyproject.presentation.ui.multipane.details.ArticleDetailsContent
import app.duss.easyproject.presentation.ui.project.details.components.PokemonInfos
import app.duss.easyproject.presentation.ui.project.details.components.PokemonStats
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun mProjectDetailsContent(
    component: ArticleDetailsComponent<Project>,
) {

    val state = component.models.subscribeAsState()

    ArticleDetailsContent(
        component,
        modifier = Modifier,
        title = component.models.value.item.code,
    ) {
        Box(contentAlignment = Alignment.TopCenter) {

            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(
                                onClick = { component.onCloseClicked() }
                            ) {
                                Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                            }
                        },
                        title = {
                            state.value.item?.let { pokemonInfo ->
                                Text(
                                    text = pokemonInfo.code.replaceFirstChar { it.uppercaseChar() },
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    state.value.item?.let { pokemonInfo ->
//                                    onEvent(
//                                        DetailsStore.Intent.UpdatePokemonFavoriteState(
////                                            isFavorite = !pokemonInfo.isFavorite
//                                        )
//                                    )
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Rounded.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        },
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                },
                containerColor = Color.Transparent,
                modifier = Modifier.padding(LocalSafeArea.current)
            ) {  paddingValue ->
                Box(
                    modifier = Modifier
                        .padding(paddingValue)
                        .padding(20.dp)
                ) {
                    if (state.value.isLoading) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    state.value.error?.let { error ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = error)
                        }
                    }

                    state.value.item?.let { pokemonInfo ->
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            item("name") {
                                Text(
                                    text = pokemonInfo.code.replaceFirstChar { it.uppercaseChar() },
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.displaySmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            item("id") {
                                Text(
                                    text = pokemonInfo.id.toString(),
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center,
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
                                    project = pokemonInfo,
                                    modifier = Modifier
                                        .padding(top = 18.dp)
                                        .fillMaxWidth(.9f)
                                )
                            }

                            item("stats") {
                                PokemonStats(
                                    project = pokemonInfo,
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
}
