package github.idmeetrious.pokemon.domain.repositories

import io.reactivex.rxjava3.core.Single

interface Repository {
    fun getPokemonById(id: Int)
}