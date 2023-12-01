package app.duss.easyproject.presentation.ui.main

import app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent
import app.duss.easyproject.presentation.ui.dashboard.DashboardComponent
import app.duss.easyproject.presentation.ui.database.DatabaseComponent
import app.duss.easyproject.presentation.ui.main.store.MainStore
import app.duss.easyproject.presentation.ui.main.store.MainStoreFactory
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

class MainComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit,

    private val dashboard: (ComponentContext, (DashboardComponent.Output) -> Unit) -> DashboardComponent= { childContext, output ->
        DashboardComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            output = output
        )
    },
    private val database: (ComponentContext, (DatabaseComponent.Output) -> Unit) -> DatabaseComponent= { childContext, output ->
        DatabaseComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            output = output
        )
    },
    private val project: (ComponentContext, searchValue: String, (ProjectSinglePaneComponent.Output) -> Unit) -> ProjectSinglePaneComponent= { childContext, searchValue, output ->
        ProjectSinglePaneComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue,
            output = output
        )
    },
    private val comingSoon: (ComponentContext, (ComingSoonComponent.Output) -> Unit) -> ComingSoonComponent = { childContext, output ->
        ComingSoonComponent(
            componentContext = childContext,
            output = output
        )
    },
): ComponentContext by componentContext {

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

    fun onOutput(output: Output) {
        output(output)
    }


    private val navigation = StackNavigation<Configuration>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Dashboard,
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

     fun onDashboardTabClicked() {
         navigation.bringToFront(Configuration.Dashboard)
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
            is Configuration.Dashboard -> Child.Dashboard(
                dashboard(
                    componentContext,
                    ::onDashboardOutput
                )
            )
            is Configuration.Database -> Child.Database(
                database(
                    componentContext,
                    ::onDatabaseOutput
                )
            )
            is Configuration.CE -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.Project -> Child.Project(
                project(
                    componentContext,
                    configuration.searchValue,
                    ::onProjectOutput
                )
            )
            is Configuration.SE -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.ComingSoon -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
        }

    sealed class Child {
        data class Dashboard(val component: DashboardComponent) : Child()

        data class Database(val component: DatabaseComponent) : Child()

        data class CE(val component: ComingSoonComponent) : Child()

//        data class CEDetails(val component: ComingSoonComponent) : Child()

        data class Project(val component: ProjectSinglePaneComponent) : Child()

//        data class ProjectDetails(val component: ProjectDetailsComponent) : Child()

        data class SE(val component: ComingSoonComponent) : Child()

//        data class SEDetails(val component: ComingSoonComponent) : Child()

//        data class SQ(val component: ComingSoonComponent) : Child()

//        data class SEDetails(val component: ComingSoonComponent) : Child()

//        data class PI(val component: ComingSoonComponent) : Child()

//        data class SEDetails(val component: ComingSoonComponent) : Child()

//        data class PO(val component: ComingSoonComponent) : Child()

//        data class SEDetails(val component: ComingSoonComponent) : Child()

        data class ComingSoon(val component: ComingSoonComponent) : Child()
    }


    sealed class Configuration : Parcelable {
        @Serializable
        data object Dashboard : Configuration()

        @Serializable
        data object Database : Configuration()

        @Serializable
        data class CE(val searchValue: String = "") : Configuration()

        @Serializable
        data class Project(val searchValue: String = "") : Configuration()
//
//        @Serializable
//        data class ProjectDetails(val id: Long?) : Configuration()

        @Serializable
        data class SE(val searchValue: String = "") : Configuration()
//
//        @Parcelize
//        data class CEDetails(val ceCode: String) : Configuration()

        @Serializable
        data object ComingSoon : Configuration()
    }


    private fun onDashboardOutput(output: DashboardComponent.Output): Unit =
        when (output) {
            DashboardComponent.Output.DatabaseClicked -> navigation.bringToFront(Configuration.Database)
//            DashboardComponent.Output.CEClicked -> navigation.push(Configuration.CE())
            DashboardComponent.Output.ProjectClicked -> navigation.bringToFront(Configuration.Project())
            is DashboardComponent.Output.SearchSubmitted -> navigation.bringToFront(
                Configuration.Project(
                    output.searchValue
                )
            )
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
            DashboardComponent.Output.Unauthorized -> TODO()
        }


    private fun onProjectOutput(output: ProjectSinglePaneComponent.Output): Unit {}
//        when (output) {
//            is ProjectComponent.Output.NavigateBack -> navigation.pop()
//            is ProjectComponent.Output.NavigateToDetails -> navigation.pushNew(
//                Configuration.ProjectDetails(
//                    output.id
//                )
//            )
//        }

    private fun onProjectDetailsOutput(output: ProjectDetailsComponent.Output): Unit =
        when (output) {
            is ProjectDetailsComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onDatabaseOutput(output: DatabaseComponent.Output): Unit =
       when (output) {
            is DatabaseComponent.Output.NavigateToCustomerDetails -> navigation.push(Configuration.ComingSoon)
            is DatabaseComponent.Output.NavigateToItemDetails -> navigation.push(Configuration.ComingSoon)
            is DatabaseComponent.Output.NavigateToPersonDetails -> navigation.push(Configuration.ComingSoon)
            is DatabaseComponent.Output.NavigateToSupplierDetails -> navigation.push(Configuration.ComingSoon)
        }

    private fun onDatabaseDetailsOutput(output: ProjectDetailsComponent.Output): Unit =
       when (output) {
            is ProjectDetailsComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onComingSoonOutput(output: ComingSoonComponent.Output): Unit =
       when (output) {
            is ComingSoonComponent.Output.NavigateBack -> navigation.pop()
        }


    sealed class Output {
        data object Unauthorized : Output()

    }

}