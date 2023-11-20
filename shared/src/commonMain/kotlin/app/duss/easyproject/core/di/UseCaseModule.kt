package app.duss.easyproject.core.di

import app.duss.easyproject.domain.usecase.FetchProjectsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { FetchProjectsUseCase( get() ) }
}