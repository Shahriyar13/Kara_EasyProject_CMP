package app.duss.easyproject.core.di

import app.duss.easyproject.domain.usecase.attachment.*
import app.duss.easyproject.domain.usecase.auth.*
import app.duss.easyproject.domain.usecase.project.*
import org.koin.dsl.module

val useCaseModule = module {
    // auth
    factory { UserLoginUseCase( get() ) }
    factory { UserLoggedInUseCase( get() ) }

    // project
    factory { ProjectsGetAllUseCase( get() ) }
    factory { ProjectGetByIdUseCase( get() ) }
    factory { ProjectGetNewUseCase( get() ) }
    factory { ProjectCreateUseCase( get() ) }
    factory { ProjectUpdateUseCase( get() ) }
    factory { ProjectDeleteUseCase( get() ) }
    factory { ProjectCodeIsValidUseCase( get() ) }

    // file attachment
    factory { AttachmentUploadUseCase( get() ) }
    factory { AttachmentDeleteUseCase( get() ) }

}