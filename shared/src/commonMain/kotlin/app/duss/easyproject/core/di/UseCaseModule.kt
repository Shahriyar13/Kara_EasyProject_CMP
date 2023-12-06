package app.duss.easyproject.core.di

import app.duss.easyproject.domain.usecase.attachment.AttachmentDeleteUseCase
import app.duss.easyproject.domain.usecase.attachment.AttachmentUploadUseCase
import app.duss.easyproject.domain.usecase.auth.UserLoggedInUseCase
import app.duss.easyproject.domain.usecase.auth.UserLoginUseCase
import app.duss.easyproject.domain.usecase.project.ProjectCreateUseCase
import app.duss.easyproject.domain.usecase.project.ProjectDeleteUseCase
import app.duss.easyproject.domain.usecase.project.ProjectGetByIdUseCase
import app.duss.easyproject.domain.usecase.project.ProjectUpdateUseCase
import app.duss.easyproject.domain.usecase.project.ProjectsGetAllUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // auth
    factory { UserLoginUseCase( get() ) }
    factory { UserLoggedInUseCase( get() ) }

    // project
    factory { ProjectsGetAllUseCase( get() ) }
    factory { ProjectGetByIdUseCase( get() ) }
    factory { ProjectCreateUseCase( get() ) }
    factory { ProjectUpdateUseCase( get() ) }
    factory { ProjectDeleteUseCase( get() ) }

    // file attachment
    factory { AttachmentUploadUseCase( get() ) }
    factory { AttachmentDeleteUseCase( get() ) }

}