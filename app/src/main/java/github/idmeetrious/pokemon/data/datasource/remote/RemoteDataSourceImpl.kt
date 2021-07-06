package github.idmeetrious.pokemon.data.datasource.remote

import github.idmeetrious.pokemon.data.api.PokeApi
import github.idmeetrious.pokemon.data.mappers.DtoToEntityMapper
import github.idmeetrious.pokemon.domain.entities.Pokemon
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

private const val TAG = "RemoteDataSourceImpl"

class RemoteDataSourceImpl(
    private val api: PokeApi,
    private val mapper: DtoToEntityMapper
) : RemoteDataSource {
    override suspend fun getPokemon(name: String): Single<Pokemon> =
//        withContext(Dispatchers.IO){
//            try {
//                var result: Result<Pokemon>? = null
//                api.getPokemonByName(name)
//                    .doOnSuccess {
//                        result = Result.Success(mapper.toPokemonEntity(it))
//                        Log.i(TAG, "--> getPokemon: result($result)")
//                    }
//                    .doOnError {
//                        it.message?.let { msg ->
//                            result = Result.Error(msg)
//                        }
//                    }
//                return@withContext result ?: Result.Loading
//            } catch (e: Exception){
//                return@withContext Result.Error(e.message ?: "Exception")
//            }
//        }
        api.getPokemonByName(name)
            .subscribeOn(Schedulers.io())
            .map { mapper.toPokemonEntity(it) }

}