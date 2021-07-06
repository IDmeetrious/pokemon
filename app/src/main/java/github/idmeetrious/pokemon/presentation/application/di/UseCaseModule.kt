package github.idmeetrious.pokemon.presentation.application.di

import dagger.Module
import dagger.Provides
import github.idmeetrious.pokemon.domain.repositories.Repository
import github.idmeetrious.pokemon.domain.usecases.GetPokemonUseCase
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetPokemonUseCase(repository: Repository): GetPokemonUseCase =
        GetPokemonUseCase(repository)
}