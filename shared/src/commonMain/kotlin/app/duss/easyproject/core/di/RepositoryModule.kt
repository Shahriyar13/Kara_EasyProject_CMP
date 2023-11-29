package app.duss.easyproject.core.di

import app.duss.easyproject.data.repository.ProjectRepositoryImpl
import app.duss.easyproject.data.repository.UserRepositoryImpl
import app.duss.easyproject.domain.repository.ProjectRepository
import app.duss.easyproject.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProjectRepository> { ProjectRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
}