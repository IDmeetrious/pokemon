package github.idmeetrious.pokemon.data.repositories

import android.util.Log
import github.idmeetrious.pokemon.data.datasource.local.LocalDataSource
import github.idmeetrious.pokemon.data.datasource.remote.RemoteDataSource
import github.idmeetrious.pokemon.domain.common.Result
import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.repositories.Repository
import io.reactivex.rxjava3.core.Single

private const val TAG = "RepositoryImpl"
class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getPokemonRemote(name: String): Single<Pokemon> {
        return remoteDataSource.getPokemon(name)
    }

    override suspend fun getFavorite(): Single<List<Pokemon>> {
        Log.i(TAG, "getFavorite: ")
        return localDataSource.getFavorite()
    }

    override suspend fun addFavorite(pokemon: Pokemon) {
        Log.i(TAG, "addFavorite: $pokemon")
        localDataSource.addFavorite(pokemon)
    }

    override suspend fun removeFavorite(pokemon: Pokemon) {
        localDataSource.removeFavorite(pokemon)
    }
}