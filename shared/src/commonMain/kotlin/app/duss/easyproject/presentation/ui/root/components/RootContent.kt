package app.duss.easyproject.presentation.ui.root.components

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import app.duss.easyproject.presentation.ui.landing.LandingScreen
import app.duss.easyproject.presentation.ui.login.LoginScreen
import app.duss.easyproject.presentation.ui.main.MainScreen
import app.duss.easyproject.presentation.ui.root.RootComponent
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation

@Composable
fun RootContent(component: RootComponent) {

    Scaffold {
        Children(
            stack = component.childStack,
            animation = stackAnimation(fade()),
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.Login -> LoginScreen(child.component)
                is RootComponent.Child.Main -> MainScreen(child.component)
                is RootComponent.Child.Landing -> LandingScreen(child.component)

            }
        }
    }
}