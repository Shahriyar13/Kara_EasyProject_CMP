package app.duss.easyproject.presentation.ui.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.login.LoginComponent
import app.duss.easyproject.presentation.ui.login.store.LoginStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginContent(
    state: LoginStore.State,
    onEvent: (LoginStore.Intent) -> Unit,
    onOutput: (LoginComponent.Output) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sign-In") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        modifier = Modifier.padding(LocalSafeArea.current)
    ) {  paddingValue ->
        Box(
            modifier = Modifier.padding(paddingValue),
            contentAlignment = Alignment.Center,
        ) {

            if (state.user != null) {
               onOutput(LoginComponent.Output.Authorized(state.user))
            }

            state.error?.let { error ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = error)
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
            ) {

                val containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .2f)

                TextField(
                    enabled = !state.isLoading,
                    value = state.username ?: "",
                    onValueChange = { onEvent(LoginStore.Intent.UpdateUsernameValue(it)) },
                    placeholder = {
                        Text(text = "Username")
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.surface,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.surface,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                    ),
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier
                        .widthIn(max = 440.dp)
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                )

                var passwordVisibility by remember { mutableStateOf(false) }

                TextField(
                    enabled = !state.isLoading,
                    value = state.password ?: "",
                    onValueChange = { onEvent(LoginStore.Intent.UpdatePasswordValue(it)) },
                    placeholder = {
                        Text(text = "Password")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisibility = !passwordVisibility }
                        ) {
                            if (!passwordVisibility) Icon(Icons.Filled.Lock , contentDescription = "Password Not Visible")
                            else Icon(Icons.Default.LockOpen , contentDescription = "Password Visible")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.surface,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.surface,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                    ),
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier
                        .widthIn(max = 440.dp)
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                )

                OutlinedButton(
                    onClick = { onEvent(LoginStore.Intent.LoginButtonPressed) },
                    enabled = !state.isLoading,
                    modifier = Modifier
                        .widthIn(max = 440.dp)
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    if (state.isLoading) CircularProgressIndicator()
                    else Text("Login")
                }
            }
        }
    }
}