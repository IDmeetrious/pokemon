package github.idmeetrious.pokemon.data.datasource.local

import github.idmeetrious.pokemon.data.db.PokemonDao
import github.idmeetrious.pokemon.data.mappers.DbToEntityMapper
import github.idmeetrious.pokemon.domain.entities.Pokemon
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
    private val pokemonDao: PokemonDao,
    private val mapperTo: DbToEntityMapper
) : LocalDataSource {

    override suspend fun getFavorite(): Single<List<Pokemon>> {
        val favorite = pokemonDao.getPokemons()
        return favorite.map { list ->
            list.map { pokemon ->
                mapperTo.toPokemonEntity(pokemon)
            }
        }
    }

    override suspend fun addFavorite(pokemon: Pokemon) =
        withContext(Dispatchers.IO) {
            pokemonDao.savePokemon(mapperTo.toPokemonDbEntity(pokemon))
        }

    override suspend fun removeFavorite(pokemon: Pokemon) =
        withContext(Dispatchers.IO) {
            pokemonDao.removePokemon(mapperTo.toPokemonDbEntity(pokemon))
        }

}