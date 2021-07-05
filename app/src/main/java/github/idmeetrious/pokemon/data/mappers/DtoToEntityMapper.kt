package github.idmeetrious.pokemon.data.mappers

import github.idmeetrious.pokemon.data.api.dto.PokemonDto
import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.entities.Sprites


class DtoToEntityMapper {
    fun toPokemonEntity(response: PokemonDto): Pokemon {
        response.let {
            return Pokemon(
                it.id,
                it.name,
                it.height,
                it.weight,
                Sprites(
                    it.sprites.backDefault,
                    it.sprites.backShiny,
                    it.sprites.frontDefault,
                    it.sprites.frontShiny
                )
            )
        }
    }
}