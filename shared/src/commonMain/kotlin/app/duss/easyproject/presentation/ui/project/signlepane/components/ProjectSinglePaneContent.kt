package app.duss.easyproject.presentation.ui.project.signlepane.components

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
import androidx.compose.ui.Modifier
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsScreen
import app.duss.easyproject.presentation.ui.project.list.ProjectListScreen
import app.duss.easyproject.presentation.ui.project.list.store.ProjectListStore
import app.duss.easyproject.presentation.ui.project.signlepane.ProjectSinglePaneComponent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProjectSinglePaneContent(
    component: ProjectSinglePaneComponent,
) {

    val onEvent: (ProjectListStore.Intent) -> Unit = component::onEvent

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Projects s") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(onClick = {
                        onEvent(ProjectListStore.Intent.AddNew)
                    }) {
                        Icon(Icons.Default.Add, "Add New one")
                    }
                }
            )
        },
        modifier = Modifier.padding(LocalSafeArea.current)
    ) { paddingValue ->

       Children(
                modifier = Modifier.padding(paddingValue),
                stack = component.childStack,
                animation = stackAnimation(fade()),
            ) {
                when (val child = it.instance) {
                    is ProjectSinglePaneComponent.Children.Details -> ProjectDetailsScreen(child.component)
                    is ProjectSinglePaneComponent.Children.List -> ProjectListScreen(child.component)
                }
            }
        }

}