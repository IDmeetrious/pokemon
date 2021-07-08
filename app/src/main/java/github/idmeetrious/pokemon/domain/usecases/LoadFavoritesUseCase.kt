package github.idmeetrious.pokemon.domain.usecases

import github.idmeetrious.pokemon.domain.repositories.Repository

class LoadFavoritesUseCase(private val repository: Repository) {
    suspend fun invoke() = repository.getFavorite()
}