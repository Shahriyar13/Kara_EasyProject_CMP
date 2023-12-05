package app.duss.easyproject.presentation.ui.root

import app.duss.easyproject.presentation.ui.landing.LandingComponent
import app.duss.easyproject.presentation.ui.login.LoginComponent
import app.duss.easyproject.presentation.ui.main.MainComponent
import app.duss.easyproject.presentation.ui.main.store.MainStore
import app.duss.easyproject.presentation.ui.main.store.MainStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
): ComponentContext by componentContext {

    private val login: (
        ComponentContext,
        (LoginComponent.Output) -> Unit,
    ) -> LoginComponent = { childContext, output ->
        LoginComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            output = output
        )
    }

    private val main: (
        ComponentContext,
        (MainComponent.Output) -> Unit,
    ) -> MainComponent = { childContext, output ->
        MainComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            output = output
        )
    }

    private val landing: (
        ComponentContext,
        (LandingComponent.Output) -> Unit,
    ) -> LandingComponent= { childContext, output ->
        LandingComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            output = output
        )
    }

    private val rootStore =
        instanceKeeper.getStore {
            MainStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MainStore.State> = rootStore.stateFlow

    fun onEvent(event: MainStore.Intent) {
        rootStore.accept(event)
    }

    private val navigation = StackNavigation<Configuration>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Landing,
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Login -> Child.Login(login(componentContext, ::onLoginOutput))
            is Configuration.Main -> Child.Main(main(componentContext, ::onMainOutput))
            is Configuration.Landing -> Child.Landing(landing(componentContext, ::onLandingOutput))
        }

    sealed class Child {
        data class Login(val component: LoginComponent) : Child()

        data class Landing(val component: LandingComponent) : Child()

        data class Main(val component: MainComponent) : Child()
    }


    sealed class Configuration : Parcelable {
        @Serializable
        data object Main : Configuration()

        @Serializable
        data object Login : Configuration()

        @Serializable
        data object Landing : Configuration()
    }


    private fun onMainOutput(output: MainComponent.Output): Unit =
        when (output) {
            MainComponent.Output.Unauthorized -> navigation.pushNew(Configuration.Login)
        }


    private fun onLandingOutput(output: LandingComponent.Output): Unit =
        when (output) {
            is LandingComponent.Output.Authorized -> navigation.pushNew(Configuration.Main)
            LandingComponent.Output.Unauthorized -> navigation.pushNew(Configuration.Login)
        }

    private fun onLoginOutput(output: LoginComponent.Output): Unit =
        when (output) {
            is LoginComponent.Output.Authorized -> {
                output.let {
                    if (it.user != null) {
                        navigation.pushNew(Configuration.Main)
                    }
                }
            }
        }
}