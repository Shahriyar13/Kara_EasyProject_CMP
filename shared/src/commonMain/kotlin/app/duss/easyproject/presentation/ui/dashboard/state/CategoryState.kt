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
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/dna.png",
            startColor = Red300,
            endColor = Red500,
        )

        val ce = CategoryState(
            title = "Customer Enquiries",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/enquiry.png",
            startColor = Yellow300,
            endColor = Yellow500,
        )

        val project = CategoryState(
            title = "Projects",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/location.png",
            startColor = Green300,
            endColor = Green500,
        )

        val se = CategoryState(
            title = "Supplier Enquiries",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/location.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val sq = CategoryState(
            title = "Supplier Quotations",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/electric.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val pi = CategoryState(
            title = "Proforma Invoices",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/electric.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val po = CategoryState(
            title = "Purchase Orders",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/electric.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val shipping = CategoryState(
            title = "Shipping Lists",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/shipping.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val invoice = CategoryState(
            title = "Invoices",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/invoice.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val bafa = CategoryState(
            title = "BAFAs",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/bafa.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val payment = CategoryState(
            title = "Payments",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/financial.png",
            startColor = Blue300,
            endColor = Blue500,
        )

        val profile = CategoryState(
            title = "Profile And Settings",
            iconUrl = "https://raw.githubusercontent.com/Shahriyar13/Kara_EasyProject_CMP/featuremvplist/icons/pokeball.png",
            startColor = Blue300,
            endColor = Blue500,
        )
    }
}
