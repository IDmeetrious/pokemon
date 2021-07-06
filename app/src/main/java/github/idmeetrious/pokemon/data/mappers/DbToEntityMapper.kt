package github.idmeetrious.pokemon.data.mappers

import github.idmeetrious.pokemon.data.db.entities.PokemonData
import github.idmeetrious.pokemon.data.db.entities.SpritesData
import github.idmeetrious.pokemon.domain.entities.Pokemon
import github.idmeetrious.pokemon.domain.entities.Sprites

class DbToEntityMapper {
    fun toPokemonDbEntity(pokemon: Pokemon): PokemonData {
        pokemon.let {
            return PokemonData(
                id = it.id,
                name = it.name,
                weight = it.weight,
                height = it.height,
                sprites = SpritesData(
                    backDefault = it.sprites.backDefault,
                    backShiny = it.sprites.backShiny,
                    frontDefault = it.sprites.frontDefault,
                    frontShiny = it.sprites.frontShiny
                )
            )
        }
    }

    fun toPokemonEntity(pokemon: PokemonData): Pokemon {
        pokemon.let {
            return Pokemon(
                id = it.id,
                name = it.name,
                weight = it.weight,
                height = it.height,
                sprites = Sprites(
                    backDefault = it.sprites.backDefault,
                    backShiny = it.sprites.backShiny,
                    frontDefault = it.sprites.frontDefault,
                    frontShiny = it.sprites.frontShiny
                )
            )
        }
    }
}