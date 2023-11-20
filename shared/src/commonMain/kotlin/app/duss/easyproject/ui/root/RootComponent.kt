package app.duss.easyproject.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import app.duss.easyproject.ui.comingsoon.ComingSoonComponent
import app.duss.easyproject.ui.details.DetailsComponent
import app.duss.easyproject.ui.favorite.FavoriteComponent
import app.duss.easyproject.ui.main.MainComponent
import app.duss.easyproject.ui.project.ProjectComponent

class RootComponent internal constructor(
    componentContext: ComponentContext,
    private val main: (ComponentContext, (MainComponent.Output) -> Unit) -> MainComponent,
    private val database: (ComponentContext, (FavoriteComponent.Output) -> Unit) -> FavoriteComponent,
    private val project: (ComponentContext, searchValue: String, (ProjectComponent.Output) -> Unit) -> ProjectComponent,
    private val projectDetails: (ComponentContext, pokemonName: String, (DetailsComponent.Output) -> Unit) -> DetailsComponent,
    private val ce: (ComponentContext, searchValue: String, (ProjectComponent.Output) -> Unit) -> ProjectComponent,
    private val ceDetails: (ComponentContext, pokemonName: String, (DetailsComponent.Output) -> Unit) -> DetailsComponent,
    private val comingSoon: (ComponentContext, (ComingSoonComponent.Output) -> Unit) -> ComingSoonComponent,
): ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
    ) : this(
        componentContext = componentContext,
        main = { childContext, output ->
            MainComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        database = { childContext, output ->
            FavoriteComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        project = { childContext, searchValue, output ->
            ProjectComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                searchValue = searchValue,
                output = output
            )
        },
        projectDetails = { childContext, pokemonName, output ->
            DetailsComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                pokemonName = pokemonName,
                output = output
            )
        },
        ce = { childContext, searchValue, output ->
            ProjectComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                searchValue = searchValue,
                output = output
            )
        },
        ceDetails = { childContext, pokemonName, output ->
            DetailsComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                pokemonName = pokemonName,
                output = output
            )
        },
        comingSoon = { childContext, output ->
            ComingSoonComponent(
                componentContext = childContext,
                output = output
            )
        },
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Main,
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Main -> Child.Main(main(componentContext, ::onMainOutput))
            is Configuration.Database -> Child.Database(database(componentContext, ::onDatabaseOutput))
            is Configuration.Project -> Child.Project(project(componentContext, configuration.searchValue, ::onProjectOutput))
            is Configuration.ProjectDetails -> Child.ProjectDetails(projectDetails(componentContext, configuration.projectCode, ::onProjectDetailsOutput))
            is Configuration.CE -> Child.CE(ce(componentContext, configuration.searchValue, ::onProjectOutput))
            is Configuration.CEDetails -> Child.CEDetails(ceDetails(componentContext, configuration.ceCode, ::onProjectDetailsOutput))
            is Configuration.ComingSoon -> Child.ComingSoon(comingSoon(componentContext, ::onComingSoonOutput))
        }

    private fun onMainOutput(output: MainComponent.Output): Unit =
        when (output) {
            MainComponent.Output.MainClicked -> navigation.push(Configuration.Main)
            MainComponent.Output.DatabaseClicked -> navigation.push(Configuration.Database)
            MainComponent.Output.CEClicked -> navigation.push(Configuration.CE())
            MainComponent.Output.ProjectClicked -> navigation.push(Configuration.Project())
            is MainComponent.Output.SearchSubmitted -> navigation.push(Configuration.Project(output.searchValue))
            MainComponent.Output.SEClicked -> navigation.push(Configuration.ComingSoon)
            MainComponent.Output.SQClicked -> navigation.push(Configuration.ComingSoon)
            MainComponent.Output.PIClicked -> navigation.push(Configuration.ComingSoon)
            MainComponent.Output.POClicked -> navigation.push(Configuration.ComingSoon)
            MainComponent.Output.ShippingClicked -> navigation.push(Configuration.ComingSoon)
            MainComponent.Output.InvoiceClicked -> navigation.push(Configuration.ComingSoon)
            MainComponent.Output.PaymentClicked -> navigation.push(Configuration.ComingSoon)
            MainComponent.Output.BafaClicked -> navigation.push(Configuration.Database)
            MainComponent.Output.ProfileClicked -> navigation.push(Configuration.ComingSoon)
        }

    private fun onProjectOutput(output: ProjectComponent.Output): Unit =
        when (output) {
            is ProjectComponent.Output.NavigateBack -> navigation.pop()
            is ProjectComponent.Output.NavigateToDetails -> navigation.push(Configuration.ProjectDetails(output.name))
        }

    private fun onProjectDetailsOutput(output: DetailsComponent.Output): Unit =
        when (output) {
            is DetailsComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onDatabaseOutput(output: FavoriteComponent.Output): Unit =
        when (output) {
            is FavoriteComponent.Output.NavigateBack -> navigation.pop()
            is FavoriteComponent.Output.NavigateToDetails -> navigation.push(Configuration.ProjectDetails(output.name))
        }

    private fun onDatabaseDetailsOutput(output: DetailsComponent.Output): Unit =
        when (output) {
            is DetailsComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onComingSoonOutput(output: ComingSoonComponent.Output): Unit =
        when (output) {
            is ComingSoonComponent.Output.NavigateBack -> navigation.pop()
        }

    private sealed class Configuration: Parcelable {
        @Parcelize
        data object Main : Configuration()

        @Parcelize
        data object Database : Configuration()

        @Parcelize
        data class Project(val searchValue: String = "") : Configuration()

        @Parcelize
        data class ProjectDetails(val projectCode: String) : Configuration()

        @Parcelize
        data class CE(val searchValue: String = "") : Configuration()

        @Parcelize
        data class CEDetails(val ceCode: String) : Configuration()

        @Parcelize
        data object ComingSoon : Configuration()
    }

    sealed class Child {
        data class Main(val component: MainComponent) : Child()

        data class Database(val component: FavoriteComponent) : Child()

        data class Project(val component: ProjectComponent) : Child()
        data class ProjectDetails(val component: DetailsComponent) : Child()

        data class CE(val component: ProjectComponent) : Child()
        data class CEDetails(val component: DetailsComponent) : Child()

        data class ComingSoon(val component: ComingSoonComponent) : Child()
    }

}