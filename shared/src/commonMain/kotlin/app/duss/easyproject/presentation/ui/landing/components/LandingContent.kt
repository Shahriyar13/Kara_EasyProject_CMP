package app.duss.easyproject.presentation.ui.landing.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.landing.LandingComponent
import app.duss.easyproject.presentation.ui.landing.store.LandingStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LandingContent(
    state: LandingStore.State,
    onEvent: (LandingStore.Intent) -> Unit,
    onOutput: (LandingComponent.Output) -> Unit,
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

            if (!state.isLoading) {
                if (state.user != null) {
                    onOutput(LandingComponent.Output.Authorized(state.user))
                } else {
                    onOutput(LandingComponent.Output.Unauthorized)
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

            Column {

                CircularProgressIndicator()

                Divider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = .4f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )

                Text(
                    text = "Loading",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp, bottom = 6.dp)
                )

            }
        }
    }
}