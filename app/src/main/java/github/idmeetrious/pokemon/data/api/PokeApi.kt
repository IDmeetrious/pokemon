package github.idmeetrious.pokemon.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {
    @GET("/pokemon/{id}/")
    fun getPokemonById(
        @Path("id") id: Long
    ): Single<Dto.Pokemon>
}