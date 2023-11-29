package app.duss.easyproject.presentation.ui.root

import app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent
import app.duss.easyproject.presentation.ui.dashboard.DashboardComponent
import app.duss.easyproject.presentation.ui.database.DatabaseComponent
import app.duss.easyproject.presentation.ui.login.LoginComponent
import app.duss.easyproject.presentation.ui.project.details.ProjectDetailsComponent
import app.duss.easyproject.presentation.ui.project.list.ProjectComponent
import app.duss.easyproject.presentation.ui.root.store.RootStore
import app.duss.easyproject.presentation.ui.root.store.RootStoreFactory
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
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class RootComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val login: (ComponentContext, (LoginComponent.Output) -> Unit) -> LoginComponent = { childContext, output ->
        LoginComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            output = output
        )
    },
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
    private val project: (ComponentContext, searchValue: String, (ProjectComponent.Output) -> Unit) -> ProjectComponent= { childContext, searchValue, output ->
        ProjectComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue,
            output = output
        )
    },
    private val projectDetails: (ComponentContext, id: Long?, (ProjectDetailsComponent.Output) -> Unit) -> ProjectDetailsComponent = { childContext, id, output ->
        ProjectDetailsComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            id = id,
            output = output
        )
    },
//    private val ce: (ComponentContext, searchValue: String, (ProjectComponent.Output) -> Unit) -> ProjectComponent,
//    private val ceDetails: (ComponentContext, pokemonName: String, (ProjectDetailsComponent.Output) -> Unit) -> ProjectDetailsComponent,
    private val comingSoon: (ComponentContext, (ComingSoonComponent.Output) -> Unit) -> ComingSoonComponent = { childContext, output ->
        ComingSoonComponent(
            componentContext = childContext,
            output = output
        )
    },
): ComponentContext by componentContext {

    private val rootStore =
        instanceKeeper.getStore {
            RootStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<RootStore.State> = rootStore.stateFlow

    fun onEvent(event: RootStore.Intent) {
        rootStore.accept(event)
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
         if (state.value.user == null)
             navigation.bringToFront(Configuration.Login)
         else navigation.bringToFront(Configuration.Dashboard)
    }

     fun onDatabaseTabClicked() {
         if (state.value.user == null)
             navigation.bringToFront(Configuration.Login)
         else navigation.bringToFront(Configuration.Database)
    }

    fun onCETabClicked() {
        if (state.value.user == null)
            navigation.bringToFront(Configuration.Login)
        else navigation.bringToFront(Configuration.CE())
    }

     fun onProjectTabClicked() {
         if (state.value.user == null)
             navigation.bringToFront(Configuration.Login)
         else navigation.bringToFront(Configuration.Project())
    }


     fun onSETabClicked() {
         if (state.value.user == null)
             navigation.bringToFront(Configuration.Login)
         else navigation.bringToFront(Configuration.SE())
    }

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Login -> Child.Login(login(componentContext, ::onLoginOutput))
            is Configuration.Dashboard -> Child.Dashboard(dashboard(componentContext, ::onDashboardOutput))
            is Configuration.Database -> Child.Database(database(componentContext, ::onDatabaseOutput))
            is Configuration.CE -> Child.ComingSoon(comingSoon(componentContext, ::onComingSoonOutput))
            is Configuration.Project -> Child.Project(project(componentContext, configuration.searchValue, ::onProjectOutput))
            is Configuration.ProjectDetails -> Child.ProjectDetails(projectDetails(componentContext, configuration.id, ::onProjectDetailsOutput))
            is Configuration.SE -> Child.ComingSoon(comingSoon(componentContext, ::onComingSoonOutput))
            is Configuration.ComingSoon -> Child.ComingSoon(comingSoon(componentContext, ::onComingSoonOutput))
        }

    sealed class Child {
        data class Login(val component: LoginComponent) : Child()

        data class Dashboard(val component: DashboardComponent) : Child()

        data class Database(val component: DatabaseComponent) : Child()

        data class CE(val component: ComingSoonComponent) : Child()

//        data class CEDetails(val component: ComingSoonComponent) : Child()

        data class Project(val component: ProjectComponent) : Child()

        data class ProjectDetails(val component: ProjectDetailsComponent) : Child()

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
        @Parcelize
        data object Dashboard : Configuration()

        @Parcelize
        data object Login : Configuration()

        @Parcelize
        data object Database : Configuration()

        @Parcelize
        data class CE(val searchValue: String = "") : Configuration()

        @Parcelize
        data class Project(val searchValue: String = "") : Configuration()

        @Parcelize
        data class ProjectDetails(val id: Long?) : Configuration()

        @Parcelize
        data class SE(val searchValue: String = "") : Configuration()
//
//        @Parcelize
//        data class CEDetails(val ceCode: String) : Configuration()

        @Parcelize
        data object ComingSoon : Configuration()
    }


    private fun onDashboardOutput(output: DashboardComponent.Output): Unit =
        if (state.value.user == null)
            navigation.push(Configuration.Login)
        else when (output) {
            DashboardComponent.Output.Unauthorized -> navigation.push(Configuration.Login)
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
        }

    private fun onLoginOutput(output: LoginComponent.Output): Unit =
        if (state.value.user == null)
            navigation.bringToFront(Configuration.Login)
        else when (output) {
            LoginComponent.Output.NavigateToDashboard -> navigation.pop()
        }

    private fun onProjectOutput(output: ProjectComponent.Output): Unit =
        if (state.value.user == null)
            navigation.bringToFront(Configuration.Login)
        else when (output) {
//            is ProjectComponent.Output.NavigateBack -> navigation.pop()
            is ProjectComponent.Output.NavigateToDetails -> navigation.push(
                Configuration.ProjectDetails(
                    output.id
                )
            )
        }

    private fun onProjectDetailsOutput(output: ProjectDetailsComponent.Output): Unit =
        if (state.value.user == null)
            navigation.bringToFront(Configuration.Login)
        else when (output) {
            is ProjectDetailsComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onDatabaseOutput(output: DatabaseComponent.Output): Unit =
        if (state.value.user == null)
            navigation.bringToFront(Configuration.Login)
        else when (output) {
            is DatabaseComponent.Output.NavigateToCustomerDetails -> navigation.push(Configuration.ComingSoon)
            is DatabaseComponent.Output.NavigateToItemDetails -> navigation.push(Configuration.ComingSoon)
            is DatabaseComponent.Output.NavigateToPersonDetails -> navigation.push(Configuration.ComingSoon)
            is DatabaseComponent.Output.NavigateToSupplierDetails -> navigation.push(Configuration.ComingSoon)
        }

    private fun onDatabaseDetailsOutput(output: ProjectDetailsComponent.Output): Unit =
        if (state.value.user == null)
            navigation.bringToFront(Configuration.Login)
        else when (output) {
            is ProjectDetailsComponent.Output.NavigateBack -> navigation.pop()
        }

    private fun onComingSoonOutput(output: ComingSoonComponent.Output): Unit =
        if (state.value.user == null)
            navigation.bringToFront(Configuration.Login)
        else when (output) {
            is ComingSoonComponent.Output.NavigateBack -> navigation.pop()
        }
}