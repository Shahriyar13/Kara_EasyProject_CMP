package app.duss.easyproject.presentation.ui.project.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Save
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

        if (state.isDeleted) {
            onOutput(ProjectDetailsComponent.Output.NavigateBack)
        }

        state.projectInfo?.let { item ->

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
                        if (state.projectInfo != null) {
                            if (state.inEditeMode) {
                                IconButton(
                                    onClick = {
                                        if (!state.isLoading)
                                            onEvent(ProjectDetailsStore.Intent.EditState)
                                    }
                                ) {
                                    Icon(
                                        Icons.Rounded.Save,
                                        contentDescription = "Save",
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }

                            } else {
                                IconButton(
                                    onClick = { onEvent(ProjectDetailsStore.Intent.EditState) }
                                ) {
                                    Icon(
                                        Icons.Rounded.Edit,
                                        contentDescription = "Edit",
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent,
            modifier = Modifier.padding(LocalSafeArea.current)
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