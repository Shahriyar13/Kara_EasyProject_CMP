package app.duss.easyproject.presentation.ui.mproject

import androidx.compose.runtime.Composable
import app.duss.easyproject.domain.entity.Project
import app.duss.easyproject.presentation.ui.mproject.components.mProjectDetailsContent
import app.duss.easyproject.presentation.ui.mproject.components.mProjectItemContent
import app.duss.easyproject.presentation.ui.multipane.MultiPaneContent

@Composable
fun mProjectScreen(component: mProjectComponent) {

    MultiPaneContent(
        component = component,
        detailsItemContent = { mProjectDetailsContent(it) },
        listItemContent = {
            mProjectItemContent(it)
        }
    )
}