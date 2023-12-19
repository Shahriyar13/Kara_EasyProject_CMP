package app.duss.easyproject.presentation.ui.main

import app.duss.easyproject.presentation.ui.ce.list.CEListComponent
import app.duss.easyproject.presentation.ui.comingsoon.ComingSoonComponent
import app.duss.easyproject.presentation.ui.dashboard.DashboardComponent
import app.duss.easyproject.presentation.ui.database.DatabaseComponent
import app.duss.easyproject.presentation.ui.packing.list.PackingListComponent
import app.duss.easyproject.presentation.ui.project.list.ProjectListComponent
import app.duss.easyproject.presentation.ui.quotation.list.QuotationListComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
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
    private val ce: (
        ComponentContext,
        searchValue: String?,
        (CEListComponent.Output) -> Unit,
    ) -> CEListComponent= { childContext, searchValue, output ->
        CEListComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue ?: "",
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

    private val quotation: (
        ComponentContext,
        searchValue: String?,
        (QuotationListComponent.Output) -> Unit,
    ) -> QuotationListComponent= { childContext, searchValue, output ->
        QuotationListComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            searchValue = searchValue ?: "",
            output = output
        )
    }

    private val packing: (
        ComponentContext,
        searchValue: String?,
        (PackingListComponent.Output) -> Unit,
    ) -> PackingListComponent= { childContext, searchValue, output ->
        PackingListComponent(
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

    fun onOutput(output: Output) {
        output(output)
    }


    private val navigation = StackNavigation<Configuration>()

    @OptIn(InternalSerializationApi::class)
    private val stack =
        childStack(
            source = navigation,
            serializer = Configuration::class.serializerOrNull(),
            initialConfiguration = Configuration.DashboardConfig,
            handleBackButton = false,
            childFactory = ::createChild
        )

    val childStack: Value<ChildStack<*, Child>> = stack

     fun onDashboardTabClicked() {
         navigation.bringToFront(Configuration.DashboardConfig)
    }

     fun onDatabaseTabClicked() {
         navigation.bringToFront(Configuration.DatabaseConfig)
    }

    fun onCETabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.CEConfig(searchValue))
    }

     fun onProjectTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.ProjectConfig(searchValue))
    }

     fun onSETabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.SEConfig(searchValue))
    }

    fun onQuotationTabClicked(searchValue: String? = null) {
        navigation.bringToFront(Configuration.QuotationConfig(searchValue))
    }

     fun onPITabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.PIConfig(searchValue))
    }

     fun onPOTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.POConfig(searchValue))
    }

     fun onPackingTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.PackingConfig(searchValue))
    }

    fun onInvoiceTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.InvoiceConfig(searchValue))
    }

    fun onBafaTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.BafaConfig(searchValue))
    }

    fun onPaymentTabClicked(searchValue: String? = null) {
         navigation.bringToFront(Configuration.PaymentConfig(searchValue))
    }

    fun onProfileTabClicked() {
         navigation.bringToFront(Configuration.ProfileConfig)
    }

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.DashboardConfig -> Child.DashboardChild(
                dashboard(
                    componentContext,
                    ::onDashboardOutput
                )
            )
            is Configuration.DatabaseConfig -> Child.DatabaseChild(
                database(
                    componentContext,
                    ::onDatabaseOutput
                )
            )
            is Configuration.CEConfig -> Child.CEChild(
                ce(
                    componentContext,
                    configuration.searchValue,
                    ::onCEOutput
                )
            )
            is Configuration.ProjectConfig -> Child.ProjectChild(
                project(
                    componentContext,
                    configuration.searchValue,
                    ::onProjectOutput
                )
            )
            is Configuration.SEConfig -> Child.ComingSoonChild(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.ComingSoonConfig -> Child.ComingSoonChild(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.QuotationConfig -> Child.QuotationChild(
                quotation(
                    componentContext,
                    configuration.searchValue,
                    ::onQuotationOutput
                )
            )
            is Configuration.PIConfig -> Child.ComingSoonChild(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.POConfig -> Child.ComingSoonChild(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.PackingConfig -> Child.PackingChild(
                packing(
                    componentContext,
                    configuration.searchValue,
                    ::onPackingOutput
                )
            )
            is Configuration.InvoiceConfig -> Child.ComingSoonChild(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.BafaConfig -> Child.ComingSoonChild(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            is Configuration.PaymentConfig -> Child.ComingSoonChild(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )
            Configuration.ProfileConfig -> Child.ComingSoonChild(
                comingSoon(
                    componentContext,
                    ::onComingSoonOutput
                )
            )

        }

    sealed class Child {
        data class DashboardChild(val component: DashboardComponent) : Child()

        data class DatabaseChild(val component: DatabaseComponent) : Child()

        data class CEChild(val component: CEListComponent) : Child()

        data class ProjectChild(val component: ProjectListComponent) : Child()

        data class SEChild(val component: ComingSoonComponent) : Child()

        data class QuotationChild(val component: QuotationListComponent) : Child()

        data class PIChild(val component: ComingSoonComponent) : Child()

        data class POChild(val component: ComingSoonComponent) : Child()

        data class PackingChild(val component: PackingListComponent) : Child()

        data class InvoiceChild(val component: ComingSoonComponent) : Child()

        data class PaymentChild(val component: ComingSoonComponent) : Child()

        data class BAFAChild(val component: ComingSoonComponent) : Child()

        data class ProfileChild(val component: ComingSoonComponent) : Child()

        data class ComingSoonChild(val component: ComingSoonComponent) : Child()
    }


    sealed class Configuration {
        @Serializable
        data object DashboardConfig : Configuration()

        @Serializable
        data object DatabaseConfig : Configuration()

        @Serializable
        data class CEConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class ProjectConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class SEConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class QuotationConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class PIConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class POConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class PackingConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class InvoiceConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class BafaConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data class PaymentConfig(val searchValue: String? = "") : Configuration()

        @Serializable
        data object ProfileConfig : Configuration()

        @Serializable
        data object ComingSoonConfig : Configuration()
    }


    private fun onDashboardOutput(output: DashboardComponent.Output): Unit =
        when (output) {
            DashboardComponent.Output.DatabaseClicked -> onDatabaseTabClicked()
            DashboardComponent.Output.CEClicked -> onCETabClicked()
            DashboardComponent.Output.ProjectClicked -> onProjectTabClicked()
            is DashboardComponent.Output.SearchSubmitted -> onProjectTabClicked(output.searchValue)
            DashboardComponent.Output.SEClicked -> onSETabClicked()
            DashboardComponent.Output.QuotationClicked -> onQuotationTabClicked()
            DashboardComponent.Output.PIClicked -> onPITabClicked()
            DashboardComponent.Output.POClicked -> onPOTabClicked()
            DashboardComponent.Output.PackingClicked -> onPackingTabClicked()
            DashboardComponent.Output.InvoiceClicked -> onInvoiceTabClicked()
            DashboardComponent.Output.PaymentClicked -> onPaymentTabClicked()
            DashboardComponent.Output.BafaClicked -> onBafaTabClicked()
            DashboardComponent.Output.ProfileClicked -> onProfileTabClicked()
            DashboardComponent.Output.Unauthorized -> output(Output.Unauthorized)
            DashboardComponent.Output.ComingSoonClicked -> navigation.bringToFront(Configuration.ComingSoonConfig)
        }


    private fun onProjectOutput(output: ProjectListComponent.Output) {}
    private fun onCEOutput(output: CEListComponent.Output) {}
    private fun onPackingOutput(output: PackingListComponent.Output) {}
    private fun onQuotationOutput(output: QuotationListComponent.Output) {}

    private fun onDatabaseOutput(output: DatabaseComponent.Output): Unit = Unit

    private fun onComingSoonOutput(output: ComingSoonComponent.Output): Unit =
       when (output) {
            is ComingSoonComponent.Output.NavigateBack -> navigation.pop()
        }


    sealed class Output {
        data object Unauthorized : Output()

    }

}