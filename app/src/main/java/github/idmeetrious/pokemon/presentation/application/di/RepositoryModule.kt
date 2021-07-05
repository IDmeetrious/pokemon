package github.idmeetrious.pokemon.presentation.application.di

import dagger.Module
import dagger.Provides
import github.idmeetrious.pokemon.data.repositories.RepositoryImpl
import github.idmeetrious.pokemon.domain.repositories.Repository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(repository: RepositoryImpl): Repository = repository
}