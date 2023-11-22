package app.duss.easyproject.core.di

import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.data.repository.ProjectRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<ProjectRepository> { ProjectRepositoryImpl() }
}