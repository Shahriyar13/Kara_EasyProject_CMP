package app.duss.easyproject.presentation.ui.multipane.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
internal fun <T> ArticleDetailsContent(
    component: ArticleDetailsComponent<T>,
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val model by component.models.subscribeAsState()
    val item = model.item

    Column(modifier = modifier) {
        if (model.isToolbarVisible) {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = component::onCloseClicked) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }

        content()
    }
}
