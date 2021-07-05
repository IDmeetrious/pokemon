package github.idmeetrious.pokemon.data.repositories

import github.idmeetrious.pokemon.data.datasource.local.LocalDataSource
import github.idmeetrious.pokemon.data.datasource.remote.RemoteDataSource
import github.idmeetrious.pokemon.domain.common.Result
import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.repositories.Repository
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getPokemonRemote(id: Int): Result<Pokemon> {
        return remoteDataSource.getPokemon(id)
    }

    override suspend fun getFavorite(): Single<List<Pokemon>> {
        return localDataSource.getFavorite()
    }

    override suspend fun addFavorite(pokemon: Pokemon) {
        localDataSource.addFavorite(pokemon)
    }

    override suspend fun removeFavorite(pokemon: Pokemon) {
        localDataSource.removeFavorite(pokemon)
    }
}