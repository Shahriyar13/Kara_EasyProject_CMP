package app.duss.easyproject.presentation.ui.main

import app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent
import app.duss.easyproject.presentation.ui.dashboard.DashboardComponent
import app.duss.easyproject.presentation.ui.database.DatabaseComponent
import app.duss.easyproject.presentation.ui.main.store.MainStore
import app.duss.easyproject.presentation.ui.main.store.MainStoreFactory
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializerOrNull

class MainComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
): ComponentContext by componentContext {


    private val dashboard: (
        ComponentContext,
        (DashboardComponent.Output) -> Unit,
    ) -> DashboardComponent= { childContext, output ->
        DashboardComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            output = output
        )
    }
    private val database: (
        ComponentContext,
        (DatabaseComponent.Output) -> Unit,
    ) -> DatabaseComponent= { childContext, output ->
        DatabaseComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            output = output
        )
    }
    private val project: (
        ComponentContext,
        searchValue: String?,
        (ProjectListComponent.Output) -> Unit,
    ) -> ProjectListComponent= { childContext, searchValue, output ->
        ProjectListComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue ?: "",
            output = output
        )
    }
    private val comingSoon: (
        ComponentContext,
        (ComingSoonComponent.Output) -> Unit,
    ) -> ComingSoonComponent = { childContext, output ->
        ComingSoonComponent(
            componentContext = childContext,
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

    fun onOutput(output: Output) {
        output(output)
    }


    private val navigation = StackNavigation<Configuration>()

    @OptIn(InternalSerializationApi::class)
    private val stack =
        childStack(
            source = navigation,
            serializer = Configuration::class.serializerOrNull(),
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

    fun onCETabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.CE(searchValue))
    }

     fun onProjectTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.Project(searchValue))
    }

     fun onSETabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.SE(searchValue))
    }

    fun onSQTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.SQ(searchValue))
    }

     fun onPITabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.PI(searchValue))
    }

     fun onPOTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.PO(searchValue))
    }

     fun onPackingTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.Packing(searchValue))
    }

    fun onInvoiceTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.Invoice(searchValue))
    }

    fun onBafaTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.Bafa(searchValue))
    }

    fun onPaymentTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.Payment(searchValue))
    }

    fun onProfileTabClicked() {
         navigation.bringToFront(Configuration.Profile)
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
            is Configuration.SQ -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.PI -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.PO -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.Packing -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.Invoice -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.Bafa -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.Payment -> Child.ComingSoon(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            Configuration.Profile -> Child.ComingSoon(
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

        data class Project(val component: ProjectListComponent) : Child()

        data class SE(val component: ComingSoonComponent) : Child()

        data class SQ(val component: ComingSoonComponent) : Child()

        data class PI(val component: ComingSoonComponent) : Child()

        data class PO(val component: ComingSoonComponent) : Child()

        data class Packing(val component: ComingSoonComponent) : Child()

        data class Invoice(val component: ComingSoonComponent) : Child()

        data class Payment(val component: ComingSoonComponent) : Child()

        data class BAFA(val component: ComingSoonComponent) : Child()

        data class Profile(val component: ComingSoonComponent) : Child()

        data class ComingSoon(val component: ComingSoonComponent) : Child()
    }


    sealed class Configuration {
        @Serializable
        data object Dashboard : Configuration()

        @Serializable
        data object Database : Configuration()

        @Serializable
        data class CE(val searchValue: String? = "") : Configuration()

        @Serializable
        data class Project(val searchValue: String? = "") : Configuration()

        @Serializable
        data class SE(val searchValue: String? = "") : Configuration()

        @Serializable
        data class SQ(val searchValue: String? = "") : Configuration()

        @Serializable
        data class PI(val searchValue: String? = "") : Configuration()

        @Serializable
        data class PO(val searchValue: String? = "") : Configuration()

        @Serializable
        data class Packing(val searchValue: String? = "") : Configuration()

        @Serializable
        data class Invoice(val searchValue: String? = "") : Configuration()

        @Serializable
        data class Bafa(val searchValue: String? = "") : Configuration()

        @Serializable
        data class Payment(val searchValue: String? = "") : Configuration()

        @Serializable
        data object Profile : Configuration()

        @Serializable
        data object ComingSoon : Configuration()
    }


    private fun onDashboardOutput(output: DashboardComponent.Output): Unit =
        when (output) {
            DashboardComponent.Output.DatabaseClicked -> onDatabaseTabClicked()
            DashboardComponent.Output.CEClicked -> onCETabClicked()
            DashboardComponent.Output.ProjectClicked -> onProjectTabClicked()
            is DashboardComponent.Output.SearchSubmitted -> onProjectTabClicked(output.searchValue)
            DashboardComponent.Output.SEClicked -> onSETabClicked()
            DashboardComponent.Output.SQClicked -> onSQTabClicked()
            DashboardComponent.Output.PIClicked -> onPITabClicked()
            DashboardComponent.Output.POClicked -> onPOTabClicked()
            DashboardComponent.Output.PackingClicked -> onPackingTabClicked()
            DashboardComponent.Output.InvoiceClicked -> onInvoiceTabClicked()
            DashboardComponent.Output.PaymentClicked -> onPaymentTabClicked()
            DashboardComponent.Output.BafaClicked -> onBafaTabClicked()
            DashboardComponent.Output.ProfileClicked -> onProfileTabClicked()
            DashboardComponent.Output.Unauthorized -> output(Output.Unauthorized)
            DashboardComponent.Output.ComingSoonClicked -> navigation.bringToFront(Configuration.ComingSoon)
        }


    private fun onProjectOutput(output: ProjectListComponent.Output) {}

    private fun onDatabaseOutput(output: DatabaseComponent.Output): Unit =
       when (output) {
            is DatabaseComponent.Output.NavigateToCustomerDetails -> navigation.push(Configuration.ComingSoon)
            is DatabaseComponent.Output.NavigateToItemDetails -> navigation.push(Configuration.ComingSoon)
            is DatabaseComponent.Output.NavigateToPersonDetails -> navigation.push(Configuration.ComingSoon)
            is DatabaseComponent.Output.NavigateToSupplierDetails -> navigation.push(Configuration.ComingSoon)
        }

    private fun onComingSoonOutput(output: ComingSoonComponent.Output): Unit =
       when (output) {
            is ComingSoonComponent.Output.NavigateBack -> navigation.pop()
        }


    sealed class Output {
        data object Unauthorized : Output()

    }

}