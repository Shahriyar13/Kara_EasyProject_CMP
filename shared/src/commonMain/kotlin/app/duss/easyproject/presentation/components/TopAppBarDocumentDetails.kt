package app.duss.easyproject.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDocumentDetails(
    title: String?,
    onBack: (() -> Unit)? = null,
    onSave: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    editState: Boolean = false,
    loadingState: Boolean = false,
    showUnsavedChanges: Boolean = false,
) {
    Column {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = { onBack?.invoke() }
                ) {
                    Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                }
            },
            title = {
                Text(
                    text = if (showUnsavedChanges) "Unsaved Changes" else if (loadingState) "wait..." else title ?: "",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            actions = {
                if (onSave != null && editState) {
                    IconButton(
                        onClick = { onSave() }
                    ) {
                        Icon(
                            Icons.Rounded.Save,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else if (onEdit != null) {
                    IconButton(
                        onClick = { onEdit() }
                    ) {
                        Icon(
                            Icons.Rounded.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.Transparent
            ),
        )
        if (loadingState) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}