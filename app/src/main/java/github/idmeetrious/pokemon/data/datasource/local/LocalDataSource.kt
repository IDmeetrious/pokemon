package github.idmeetrious.pokemon.data.datasource.local

import github.idmeetrious.pokemon.domain.entities.Pokemon
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {
    suspend fun getFavorite(): Single<List<Pokemon>>
    suspend fun addFavorite(pokemon: Pokemon)
    suspend fun removeFavorite(pokemon: Pokemon)
}