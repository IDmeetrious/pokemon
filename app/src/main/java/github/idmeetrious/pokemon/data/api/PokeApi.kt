package github.idmeetrious.pokemon.data.api

import github.idmeetrious.pokemon.data.api.dto.PokemonDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {
    @GET("pokemon/{name}/")
    fun getPokemonByName(
        @Path("name") name: String = "1"
    ): Single<PokemonDto>
}