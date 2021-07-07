package github.idmeetrious.pokemon.domain.repositories

import github.idmeetrious.pokemon.domain.entities.Pokemon
import io.reactivex.rxjava3.core.Single

interface Repository {
    suspend fun getPokemonRemote(name: String): Single<Pokemon>
    suspend fun getFavorite(): Single<List<Pokemon>>
    suspend fun addFavorite(pokemon: Pokemon)
    suspend fun removeFavorite(pokemon: Pokemon)
}