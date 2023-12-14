package app.duss.easyproject.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher


@Composable
fun FilePicker(
    fileType: FilePickerFileType = FilePickerFileType.All,
    onFileSelected: (List<KmpFile>)-> Unit,
) {

    val pickerLauncher = rememberFilePickerLauncher(
        type = fileType,
        selectionMode = FilePickerSelectionMode.Multiple,
        onResult = { files -> onFileSelected(files) },
    )

    Button(
        onClick = {
            pickerLauncher.launch()
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Pick Files")
    }

}