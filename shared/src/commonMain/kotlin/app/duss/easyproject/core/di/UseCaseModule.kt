package app.duss.easyproject.core.di

import app.duss.easyproject.domain.usecase.FetchProjectsUseCase
import app.duss.easyproject.domain.usecase.UserLoggedInUseCase
import app.duss.easyproject.domain.usecase.UserLoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { FetchProjectsUseCase( get() ) }
    factory { UserLoginUseCase( get() ) }
    factory { UserLoggedInUseCase( get() ) }
}