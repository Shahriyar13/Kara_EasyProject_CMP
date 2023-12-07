package app.duss.easyproject.core.di

import app.duss.easyproject.data.repository.*
import app.duss.easyproject.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<ProjectRepository> { ProjectRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single<AttachmentRepository> { AttachmentRepositoryImpl() }
}