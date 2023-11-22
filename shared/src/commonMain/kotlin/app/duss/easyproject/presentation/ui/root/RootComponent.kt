package app.duss.easyproject.presentation.ui.root

import app.duss.easyproject.presentation.ui.dashboard.DashboardComponent
import app.duss.easyproject.presentation.ui.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.favorite.DatabaseComponent
import app.duss.easyproject.presentation.ui.project.ProjectComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory

class RootComponent internal constructor(
    componentContext: ComponentContext,
    private val dashboard: (ComponentContext, (DashboardComponent.Output) -> Unit) -> DashboardComponent,
    private val database: (ComponentContext, (DatabaseComponent.Output) -> Unit) -> DatabaseComponent,
    private val project: (ComponentContext, searchValue: String, (ProjectComponent.Output) -> Unit) -> ProjectComponent,
    private val projectDetails: (ComponentContext, id: Long?, (ProjectDetailsComponent.Output) -> Unit) -> ProjectDetailsComponent,
//    private val ce: (ComponentContext, searchValue: String, (ProjectComponent.Output) -> Unit) -> ProjectComponent,
//    private val ceDetails: (ComponentContext, pokemonName: String, (ProjectDetailsComponent.Output) -> Unit) -> ProjectDetailsComponent,
    private val comingSoon: (ComponentContext, (app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent.Output) -> Unit) -> app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent,
): ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
    ) : this(
        componentContext = componentContext,
        dashboard = { childContext, output ->
            DashboardComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        database = { childContext, output ->
            DatabaseComponent(
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
        projectDetails = { childContext, id, output ->
            ProjectDetailsComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                id = id,
                output = output
            )
        },
        comingSoon = { childContext, output ->
            app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent(
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


     fun onDashboardTabClicked() {
        navigation.bringToFront(Configuration.Main)
    }

     fun onDatabaseTabClicked() {
        navigation.bringToFront(Configuration.Database)
    }

    fun onCETabClicked() {
        navigation.bringToFront(Configuration.CE())
    }

     fun onProjectTabClicked() {
        navigation.bringToFront(Configuration.Project())
    }


     fun onSETabClicked() {
        navigation.bringToFront(Configuration.SE())
    }

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Main -> Child.Dashboard(dashboard(componentContext, ::onMainOutput))
            is Configuration.Database -> Child.Database(database(componentContext, ::onDatabaseOutput))
            is Configuration.CE -> Child.ComingSoon(comingSoon(componentContext, ::onComingSoonOutput))
            is Configuration.Project -> Child.Project(project(componentContext, configuration.searchValue, ::onProjectOutput))
//            is Configuration.ProjectDetails -> Child.ProjectDetails(projectDetails(componentContext, configuration.id, ::onProjectDetailsOutput))
            is Configuration.SE -> Child.ComingSoon(comingSoon(componentContext, ::onComingSoonOutput))
            is Configuration.ComingSoon -> Child.ComingSoon(comingSoon(componentContext, ::onComingSoonOutput))
        }

    private fun onMainOutput(output: DashboardComponent.Output): Unit =
        when (output) {
            DashboardComponent.Output.DatabaseClicked -> navigation.bringToFront(Configuration.Database)
//            DashboardComponent.Output.CEClicked -> navigation.push(Configuration.CE())
            DashboardComponent.Output.ProjectClicked -> navigation.bringToFront(Configuration.Project())
            is DashboardComponent.Output.SearchSubmitted -> navigation.bringToFront(Configuration.Project(output.searchValue))
//            DashboardComponent.Output.SEClicked -> navigation.push(Configuration.ComingSoon)
//            DashboardComponent.Output.SQClicked -> navigation.push(Configuration.ComingSoon)
//            DashboardComponent.Output.PIClicked -> navigation.push(Configuration.ComingSoon)
//            DashboardComponent.Output.POClicked -> navigation.push(Configuration.ComingSoon)
//            DashboardComponent.Output.ShippingClicked -> navigation.push(Configuration.ComingSoon)
//            DashboardComponent.Output.InvoiceClicked -> navigation.push(Configuration.ComingSoon)
//            DashboardComponent.Output.PaymentClicked -> navigation.push(Configuration.ComingSoon)
//            DashboardComponent.Output.BafaClicked -> navigation.push(Configuration.Database)
//            DashboardComponent.Output.ProfileClicked -> navigation.push(Configuration.ComingSoon)
//            DashboardComponent.Output.ProfileClicked -> navigation.push(Configuration.ComingSoon)
            DashboardComponent.Output.ComingSoonClicked -> navigation.bringToFront(Configuration.ComingSoon)
        }

    private fun onProjectOutput(output: ProjectComponent.Output): Unit {}
//        when (output) {
////            is ProjectComponent.Output.NavigateBack -> navigation.pop()
//            is ProjectComponent.Output.NavigateToDetails -> navigation.push(Configuration.ProjectDetails(output.id))
//        }

    private fun onProjectDetailsOutput(output: ProjectDetailsComponent.Output): Unit =
        when (output) {
            is ProjectDetailsComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onDatabaseOutput(output: DatabaseComponent.Output): Unit =
        navigation.push(Configuration.ComingSoon)

    private fun onDatabaseDetailsOutput(output: ProjectDetailsComponent.Output): Unit =
        when (output) {
            is ProjectDetailsComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onComingSoonOutput(output: app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent.Output): Unit =
        when (output) {
            is app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent.Output.NavigateBack -> navigation.pop()
        }

    private sealed class Configuration: Parcelable {
        @Parcelize
        data object Main : Configuration()

        @Parcelize
        data object Database : Configuration()

        @Parcelize
        data class CE(val searchValue: String = "") : Configuration()

        @Parcelize
        data class Project(val searchValue: String = "") : Configuration()

//        @Parcelize
//        data class ProjectDetails(val id: Long?) : Configuration()

        @Parcelize
        data class SE(val searchValue: String = "") : Configuration()
//
//        @Parcelize
//        data class CEDetails(val ceCode: String) : Configuration()

        @Parcelize
        data object ComingSoon : Configuration()
    }

    sealed class Child {
        data class Dashboard(val component: DashboardComponent) : Child()

        data class Database(val component: DatabaseComponent) : Child()

        data class CE(val component: app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent) : Child()

//        data class CEDetails(val component: ComingSoonComponent) : Child()

        data class Project(val component: ProjectComponent) : Child()

//        data class ProjectDetails(val component: ProjectDetailsComponent) : Child()

        data class SE(val component: app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent) : Child()

//        data class SEDetails(val component: ComingSoonComponent) : Child()

//        data class SQ(val component: ComingSoonComponent) : Child()

//        data class SEDetails(val component: ComingSoonComponent) : Child()

//        data class PI(val component: ComingSoonComponent) : Child()

//        data class SEDetails(val component: ComingSoonComponent) : Child()

//        data class PO(val component: ComingSoonComponent) : Child()

//        data class SEDetails(val component: ComingSoonComponent) : Child()

        data class ComingSoon(val component: app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent) : Child()
    }

}