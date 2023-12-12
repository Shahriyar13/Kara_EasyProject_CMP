package app.duss.easyproject.core.di

import app.duss.easyproject.data.network.client.*
import app.duss.easyproject.data.network.createHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: (enableLogging: Boolean) -> Module get() = { enableLogging ->
    module {
        single { createHttpClient(get()) }
        single { AttachmentClient(httpClient = get()) }
        single { CompanyClient(httpClient = get()) }
        single { CustomerEnquiryClient(httpClient = get()) }
        single { InvoiceClient(httpClient = get()) }
        single { ItemClient(httpClient = get()) }
        single { PackingClient(httpClient = get()) }
        single { PersonClient(httpClient = get()) }
        single { ProformaInvoiceClient(httpClient = get()) }
        single { ProjectClient(httpClient = get()) }
        single { PurchaseOrderClient(httpClient = get()) }
        single { QuotationClient(httpClient = get()) }
        single { RegionClient(httpClient = get()) }
        single { SupplierEnquiryClient(httpClient = get()) }
        single { UserClient(httpClient = get()) }
    }
}