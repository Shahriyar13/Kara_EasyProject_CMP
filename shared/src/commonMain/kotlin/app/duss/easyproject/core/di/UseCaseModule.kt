package app.duss.easyproject.core.di

import org.koin.dsl.module

val useCaseModule = module {

    // file attachment
    factory { app.duss.easyproject.domain.usecase.attachment.AttachmentUploadUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.attachment.AttachmentDeleteUseCase( get() ) }

    // auth
    factory { app.duss.easyproject.domain.usecase.auth.UserLoginUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.auth.UserLoggedInUseCase( get() ) }

    // ce
    factory { app.duss.easyproject.domain.usecase.ce.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.ce.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.ce.GetAllByProjectIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.ce.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.ce.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.ce.UpdateUseCase( get() ) }

    // company
    factory { app.duss.easyproject.domain.usecase.company.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.GetAllCustomersUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.GetAllSuppliersUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.GetAllFreightForwardersUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.IsCustomerCodeValidUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.IsSupplierCodeValidUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.IsFreightForwarderCodeValidUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.company.UpdateUseCase( get() ) }

    // invoice
    factory { app.duss.easyproject.domain.usecase.invoice.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.invoice.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.invoice.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.invoice.GetAllByProjectIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.invoice.IsCodeValidUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.invoice.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.invoice.UpdateUseCase( get() ) }

    // item
    factory { app.duss.easyproject.domain.usecase.item.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.item.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.item.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.item.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.item.UpdateUseCase( get() ) }

    // packing
    factory { app.duss.easyproject.domain.usecase.packing.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.packing.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.packing.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.packing.GetAllByProjectIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.packing.IsCodeValidUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.packing.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.packing.UpdateUseCase( get() ) }

    // person
    factory { app.duss.easyproject.domain.usecase.person.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.person.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.person.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.person.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.person.UpdateUseCase( get() ) }

    // pi
    factory { app.duss.easyproject.domain.usecase.pi.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.pi.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.pi.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.pi.GetAllByProjectIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.pi.IsCodeValidUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.pi.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.pi.UpdateUseCase( get() ) }

    // po
    factory { app.duss.easyproject.domain.usecase.po.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.po.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.po.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.po.GetAllByProjectIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.po.IsCodeValidUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.po.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.po.UpdateUseCase( get() ) }

    // project
    factory { app.duss.easyproject.domain.usecase.project.ProjectsGetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.project.ProjectGetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.project.ProjectGetNewUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.project.ProjectCreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.project.ProjectUpdateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.project.ProjectDeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.project.ProjectCodeIsValidUseCase( get() ) }

    // quotation
    factory { app.duss.easyproject.domain.usecase.quotation.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.quotation.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.quotation.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.quotation.GetAllByProjectIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.quotation.IsCodeValidUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.quotation.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.quotation.UpdateUseCase( get() ) }

    // region
    factory { app.duss.easyproject.domain.usecase.region.GetAllCitiesUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.region.GetAllCountriesUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.region.GetAllStatesUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.region.GetAllCustomsPortsUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.region.GetCityByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.region.GetCountryByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.region.GetStateByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.region.GetCustomsPortByIdUseCase( get() ) }

    // se
    factory { app.duss.easyproject.domain.usecase.se.CreateUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.se.DeleteUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.se.GetAllUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.se.GetAllByProjectIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.se.IsCodeValidUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.se.GetByIdUseCase( get() ) }
    factory { app.duss.easyproject.domain.usecase.se.UpdateUseCase( get() ) }

}