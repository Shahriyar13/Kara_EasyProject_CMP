package app.duss.easyproject.presentation.ui.mproject.components

import androidx.compose.runtime.Composable
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.presentation.ui.multipane.list.ArticleListComponent
import app.duss.easyproject.presentation.ui.multipane.list.ArticleListContent
import app.duss.easyproject.presentation.ui.project.components.ProjectItem
import app.duss.easyproject.presentation.ui.project.components.ProjectLoadingItem

@Composable
internal fun mProjectItemContent(
    component: ArticleListComponent<Project>,
) {
    ArticleListContent(
        component,
        itemContent = { item, onClick ->
            ProjectItem(
                project = item,
                onClick = { onClick() },
            )
        },
        itemLoadingContent = { ProjectLoadingItem(alpha = it) },
    )
}
