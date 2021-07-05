package github.idmeetrious.pokemon.data.datasource.remote

import github.idmeetrious.pokemon.data.api.PokeApi
import github.idmeetrious.pokemon.data.mappers.DtoToEntityMapper
import github.idmeetrious.pokemon.domain.common.Result
import github.idmeetrious.pokemon.domain.entities.Pokemon
import io.reactivex.rxjava3.core.Maybe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSourceImpl(
    private val api: PokeApi,
    private val mapper: DtoToEntityMapper
) : RemoteDataSource {
    override suspend fun getPokemon(id: Int): Result<Pokemon> =
        withContext(Dispatchers.IO){
            try {
                var result: Result<Pokemon>? = null
                api.getPokemonById(id)
                    .doOnSuccess {
                        result = Result.Success(mapper.toPokemonEntity(it))
                    }
                    .doOnError {
                        it.message?.let { msg ->
                            result = Result.Error(msg)
                        }
                    }
                return@withContext result ?: Result.Loading
            } catch (e: Exception){
                return@withContext Result.Error(e.message ?: "Exception")
            }
        }

}