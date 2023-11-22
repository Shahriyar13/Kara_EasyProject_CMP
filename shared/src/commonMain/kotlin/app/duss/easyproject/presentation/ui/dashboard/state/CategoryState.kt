package app.duss.easyproject.presentation.ui.dashboard.state

import androidx.compose.ui.graphics.Color
import app.duss.easyproject.presentation.theme.Blue300
import app.duss.easyproject.presentation.theme.Blue500
import app.duss.easyproject.presentation.theme.Green300
import app.duss.easyproject.presentation.theme.Green500
import app.duss.easyproject.presentation.theme.Red300
import app.duss.easyproject.presentation.theme.Red500
import app.duss.easyproject.presentation.theme.Yellow300
import app.duss.easyproject.presentation.theme.Yellow500

data class CategoryState(
    val title: String,
    val iconUrl: String,
    val startColor: Color,
    val endColor: Color,
) {
    companion object {
        val database = CategoryState(
            title = "Database",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/pokeball.png",
            startColor = Red300,
            endColor = Red500,
        )

        val ce = CategoryState(
            title = "Customer Enquiries",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/electric.png",
            startColor = Yellow300,
            endColor = Yellow500,
        )

        val project = CategoryState(
            title = "Projects",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/dna.png",
            startColor = Green300,
            endColor = Green500,
        )

        val se = CategoryState(
            title = "Supplier Enquiries",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val sq = CategoryState(
            title = "Supplier Quotations",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val pi = CategoryState(
            title = "Proforma Invoices",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val po = CategoryState(
            title = "Purchase Orders",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val shipping = CategoryState(
            title = "Shipping Lists",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val invoice = CategoryState(
            title = "Invoices",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val bafa = CategoryState(
            title = "BAFAs",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val payment = CategoryState(
            title = "Payments",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val profile = CategoryState(
            title = "Profile And Settings",
            iconUrl = "https://raw.githubusercontent.com/M0Coding/Pokedex/main/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )
    }
}
