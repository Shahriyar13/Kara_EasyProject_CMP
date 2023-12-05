package app.duss.easyproject.presentation.component

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.MPFile


@Composable
fun FilePicker(
    fileType: List<String> = emptyList(), //listOf("jpg", "png", "doc"),
    onFileSelected: (MPFile<Any>?)-> Unit,
) {
    var showFilePicker by remember { mutableStateOf(false) }

    Button(
        onClick = { showFilePicker = true }
    ) {
        Text("Select File")
    }

    FilePicker(
        show = showFilePicker,
        fileExtensions = fileType,
    ) { file ->
        showFilePicker = false
        file
        onFileSelected(file)
    }
}

//@Composable
//fun FilePicker(
//    fileType: List<String>? = listOf("jpg", "png"),
//    onFileSelected: (String?)-> Unit,
//) {
//    var showDirPicker by remember { mutableStateOf(false) }
//
//    DirectoryPicker(showDirPicker) { path ->
//        showDirPicker = false
//        onFileSelected(path)
//    }
//}