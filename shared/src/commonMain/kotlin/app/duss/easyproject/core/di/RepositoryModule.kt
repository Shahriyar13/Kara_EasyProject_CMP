package app.duss.easyproject.core.di

import app.duss.easyproject.data.repository.AttachmentRepositoryImpl
import app.duss.easyproject.data.repository.CompanyRepositoryImpl
import app.duss.easyproject.data.repository.CustomerEnquiryRepositoryImpl
import app.duss.easyproject.data.repository.InvoiceRepositoryImpl
import app.duss.easyproject.data.repository.ItemRepositoryImpl
import app.duss.easyproject.data.repository.PackingRepositoryImpl
import app.duss.easyproject.data.repository.PersonRepositoryImpl
import app.duss.easyproject.data.repository.ProformaInvoiceRepositoryImpl
import app.duss.easyproject.data.repository.ProjectRepositoryImpl
import app.duss.easyproject.data.repository.PurchaseOrderRepositoryImpl
import app.duss.easyproject.data.repository.QuotationRepositoryImpl
import app.duss.easyproject.data.repository.RegionRepositoryImpl
import app.duss.easyproject.data.repository.SupplierEnquiryRepositoryImpl
import app.duss.easyproject.data.repository.UserRepositoryImpl
import app.duss.easyproject.domain.repository.AttachmentRepository
import app.duss.easyproject.domain.repository.CompanyRepository
import app.duss.easyproject.domain.repository.CustomerEnquiryRepository
import app.duss.easyproject.domain.repository.InvoiceRepository
import app.duss.easyproject.domain.repository.ItemRepository
import app.duss.easyproject.domain.repository.PackingRepository
import app.duss.easyproject.domain.repository.PersonRepository
import app.duss.easyproject.domain.repository.ProformaInvoiceRepository
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.repository.PurchaseOrderRepository
import app.duss.easyproject.domain.repository.QuotationRepository
import app.duss.easyproject.domain.repository.RegionRepository
import app.duss.easyproject.domain.repository.SupplierEnquiryRepository
import app.duss.easyproject.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AttachmentRepository> { AttachmentRepositoryImpl() }
    single<CompanyRepository> { CompanyRepositoryImpl() }
    single<CustomerEnquiryRepository> { CustomerEnquiryRepositoryImpl() }
    single<InvoiceRepository> { InvoiceRepositoryImpl() }
    single<ItemRepository> { ItemRepositoryImpl() }
    single<PackingRepository> { PackingRepositoryImpl() }
    single<PersonRepository> { PersonRepositoryImpl() }
    single<ProformaInvoiceRepository> { ProformaInvoiceRepositoryImpl() }
    single<ProjectRepository> { ProjectRepositoryImpl() }
    single<PurchaseOrderRepository> { PurchaseOrderRepositoryImpl() }
    single<QuotationRepository> { QuotationRepositoryImpl() }
    single<RegionRepository> { RegionRepositoryImpl() }
    single<SupplierEnquiryRepository> { SupplierEnquiryRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
}