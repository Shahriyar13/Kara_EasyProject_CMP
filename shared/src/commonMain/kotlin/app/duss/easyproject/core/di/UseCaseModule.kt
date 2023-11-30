package app.duss.easyproject.core.di

import app.duss.easyproject.domain.usecase.ProjectsGetAllUseCase
import app.duss.easyproject.domain.usecase.UserLoggedInUseCase
import app.duss.easyproject.domain.usecase.UserLoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { ProjectsGetAllUseCase( get() ) }
    factory { UserLoginUseCase( get() ) }
    factory { UserLoggedInUseCase( get() ) }
}