package github.idmeetrious.pokemon.presentation.application.di

import dagger.Module
import dagger.Provides
import github.idmeetrious.pokemon.domain.repositories.Repository
import github.idmeetrious.pokemon.domain.usecases.AddFavoriteUseCase
import github.idmeetrious.pokemon.domain.usecases.DeleteFavoriteUseCase
import github.idmeetrious.pokemon.domain.usecases.GetPokemonUseCase
import github.idmeetrious.pokemon.domain.usecases.LoadFavoritesUseCase
import javax.inject.Singleton

@Module
class UseCaseModule {
    @[Singleton Provides]
    fun provideGetPokemonUseCase(repository: Repository): GetPokemonUseCase =
        GetPokemonUseCase(repository)

    @[Singleton Provides]
    fun provideAddFavoriteUseCase(repository: Repository): AddFavoriteUseCase =
        AddFavoriteUseCase(repository)

    @[Singleton Provides]
    fun provideLoadFavoritesUseCase(repository: Repository): LoadFavoritesUseCase =
        LoadFavoritesUseCase(repository)

    @[Singleton Provides]
    fun provideDeleteFavoriteUseCase(repository: Repository): DeleteFavoriteUseCase =
        DeleteFavoriteUseCase(repository)
}