package app.duss.easyproject.presentation.ui.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            modifier = Modifier.padding(paddingValue)
        ) {

            if (state.user != null) {
               onOutput(LoginComponent.Output.NavigateToDashboard)
            }

            state.error?.let { error ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = error)
                }
            }

            Column {
                Text(
                    text = "Enter your username and password",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp, bottom = 6.dp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = .4f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )

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
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = .4f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
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
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                )

                OutlinedButton(
                    onClick = { onEvent(LoginStore.Intent.LoginButtonPressed) },
                    enabled = !state.isLoading,
                ) {
                    if (state.isLoading) CircularProgressIndicator()
                    else Text("Login")
                }
            }
        }
    }
}