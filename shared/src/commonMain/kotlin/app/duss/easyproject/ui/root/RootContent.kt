package app.duss.easyproject.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import app.duss.easyproject.ui.comingsoon.ComingSoonScreen
import app.duss.easyproject.ui.details.DetailsScreen
import app.duss.easyproject.ui.favorite.FavoriteScreen
import app.duss.easyproject.ui.main.MainScreen
import app.duss.easyproject.ui.pokedex.PokedexScreen

@Composable
internal fun RootContent(component: RootComponent) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade()),
    ) {
        when(val child = it.instance) {
            is RootComponent.Child.Main -> MainScreen(child.component)
            is RootComponent.Child.Pokedex -> PokedexScreen(child.component)
            is RootComponent.Child.Favorite -> FavoriteScreen(child.component)
            is RootComponent.Child.Details -> DetailsScreen(child.component)
            is RootComponent.Child.ComingSoon -> ComingSoonScreen(child.component)
        }
    }
}