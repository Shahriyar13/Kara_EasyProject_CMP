package app.duss.easyproject.data.di

import app.duss.easyproject.data.repository.PokemonRepository
import app.duss.easyproject.data.repository.PokemonRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl() }
}