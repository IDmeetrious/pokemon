package github.idmeetrious.pokemon.domain.usecases

import github.idmeetrious.pokemon.domain.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPokemonUseCase(private val repository: Repository) {
//    suspend operator fun invoke(name: String) = repository.getPokemonRemote(name)
    suspend operator fun invoke(name: String) =
        repository.getPokemonRemote(name)

}