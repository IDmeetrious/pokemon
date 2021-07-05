package github.idmeetrious.pokemon.domain.repositories

import github.idmeetrious.pokemon.domain.common.Result
import github.idmeetrious.pokemon.domain.entities.Pokemon
import io.reactivex.rxjava3.core.Single

interface Repository {
    suspend fun getPokemonRemote(id: Int): Result<Pokemon>
    suspend fun getFavorite(): Single<List<Pokemon>>
    suspend fun addFavorite(pokemon: Pokemon)
    suspend fun removeFavorite(pokemon: Pokemon)
}