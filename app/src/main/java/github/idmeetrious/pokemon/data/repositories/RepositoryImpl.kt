package github.idmeetrious.pokemon.data.repositories

import github.idmeetrious.pokemon.data.datasource.local.LocalDataSource
import github.idmeetrious.pokemon.data.datasource.remote.RemoteDataSource
import github.idmeetrious.pokemon.domain.repositories.Repository

class RepositoryImpl(
    localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource
) : Repository {
    override fun getPokemonById(id: Int) {
        TODO("Not yet implemented")
    }
}