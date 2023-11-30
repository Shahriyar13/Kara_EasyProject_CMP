package app.duss.easyproject.presentation.ui.project.details.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.details.store.ProjectDetailsStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsContent(
    state: ProjectDetailsStore.State,
    onEvent: (ProjectDetailsStore.Intent) -> Unit,
    onOutput: (ProjectDetailsComponent.Output) -> Unit,
    modifier: Modifier,
) {
    Box(contentAlignment = Alignment.TopCenter) {
        state.projectInfo?.let { pokemonInfo ->
//            AsyncImage(
//                url = pokemonInfo.imageUrl,
//                contentDescription = pokemonInfo.name,
//                contentScale = ContentScale.FillWidth,
//                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(3f) }),
//                modifier = Modifier
//                    .widthIn(max = 800.dp)
//                    .fillMaxWidth(.9f)
//                    .wrapContentHeight(Alignment.Top, true)
//                    .scale(1f, 1.8f)
//                    .blur(70.dp, BlurredEdgeTreatment.Unbounded)
//                    .alpha(.5f)
//            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = { onOutput(ProjectDetailsComponent.Output.NavigateBack) }
                        ) {
                            Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                        }
                    },
                    title = {
                        state.projectInfo?.let { pokemonInfo ->
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
                                state.projectInfo?.let { pokemonInfo ->
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
                if (state.isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.error?.let { error ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = error)
                    }
                }

                state.projectInfo?.let { pokemonInfo ->
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