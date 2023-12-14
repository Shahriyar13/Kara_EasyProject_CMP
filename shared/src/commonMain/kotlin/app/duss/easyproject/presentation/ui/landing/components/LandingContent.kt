package app.duss.easyproject.presentation.ui.landing.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.duss.easyproject.presentation.helper.LocalSafeArea
import app.duss.easyproject.presentation.ui.landing.LandingComponent
import app.duss.easyproject.presentation.ui.landing.store.LandingStore

@Composable
internal fun LandingContent(
    state: LandingStore.State,
    onEvent: (LandingStore.Intent) -> Unit,
    onOutput: (LandingComponent.Output) -> Unit,
) {
    Scaffold(
        modifier = Modifier.padding(LocalSafeArea.current)
    ) { paddingValue ->
        Box(
            modifier = Modifier.padding(paddingValue)
        ) {

            state.error?.let { _ ->
                onOutput(LandingComponent.Output.Unauthorized)
            }

            state.user?.let {
                onOutput(LandingComponent.Output.Authorized(state.user))
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            CircularProgressIndicator()

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