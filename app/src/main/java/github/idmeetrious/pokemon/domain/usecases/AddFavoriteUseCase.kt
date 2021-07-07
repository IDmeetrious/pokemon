package github.idmeetrious.pokemon.domain.usecases

import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.repositories.Repository

class AddFavoriteUseCase(private val repository: Repository) {
    suspend fun invoke(pokemon: Pokemon) {
        repository.addFavorite(pokemon)
    }
}